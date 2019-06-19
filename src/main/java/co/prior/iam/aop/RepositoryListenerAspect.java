package co.prior.iam.aop;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import co.prior.iam.entity.IamAuditTrail;
import co.prior.iam.repository.IamAuditTrailRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class RepositoryListenerAspect {

	private final IamAuditTrailRepository iamAuditTrailRepository;
	
	public RepositoryListenerAspect(IamAuditTrailRepository iamAuditTrailRepository) {
		this.iamAuditTrailRepository = iamAuditTrailRepository;
	}
	
	@AfterReturning(pointcut = "execution(* co.prior.iam.repository.*.save(..))"
			+ " && !execution(* co.prior.iam.repository.IamAuditTrailRepository.save(..))", returning = "currentState")
	@Transactional(propagation = Propagation.MANDATORY)
	public void postSave(Object currentState) {
		log.info("currentState: {}", currentState);
		
		Class<?> clazz = currentState.getClass();
		Object previousState = ReflectionUtils.invokeMethod(
				ReflectionUtils.findMethod(clazz, "getPreviousState"), currentState);
		log.info("previousState: {}", previousState);
		
		Optional<Field> primaryField = Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> field.getAnnotation(Id.class) != null)
				.findFirst();
		
		if (primaryField.isPresent()) {
			int runningNo = 1;
			long primaryKey = (long) ReflectionUtils.getField(primaryField.get(), currentState);
			
			List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
					.filter(field -> field.getAnnotation(Id.class) == null)
					.collect(Collectors.toList());
			for (Field field : fields) {
				if (this.saveAuditTrail(clazz, field, previousState, currentState, 
						runningNo, primaryKey).isPresent()) {
					runningNo++;
				}
			}
			
			Field isDeletedField = ReflectionUtils.findField(clazz, "isDeleted");
			this.saveAuditTrail(clazz, isDeletedField, previousState, currentState, runningNo, primaryKey);
		}
	}
	
	private Optional<IamAuditTrail> saveAuditTrail(Class<?> clazz, Field field, Object previousState, Object currentState, 
			int runningNo, long primaryKey) {
		
		ReflectionUtils.makeAccessible(field);
		Object oldValue = previousState == null? null : ReflectionUtils.getField(field, previousState);
		Object newValue = ReflectionUtils.getField(field, currentState);
		
		log.info("running no: {}", runningNo);
		log.info("field name: {}", field.getName());
		log.info("old value: {}", oldValue);
		log.info("new value: {}", newValue);
		
		if (oldValue == null || !oldValue.equals(newValue)) {
			IamAuditTrail iamAuditTrail = new IamAuditTrail();
			String tableName = clazz.getAnnotation(Table.class).name();
			iamAuditTrail.setRunningNo(runningNo);
			iamAuditTrail.setTableName(tableName);
			iamAuditTrail.setPrimaryKey(primaryKey);
			iamAuditTrail.setColumnName(field.getName().replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase());
			iamAuditTrail.setOldValue((String) oldValue);
			iamAuditTrail.setNewValue((String) newValue);
			iamAuditTrail.setIsNew(oldValue == null? "Y" : "N");
			iamAuditTrail.setIsFk(field.getAnnotation(ManyToOne.class) == null? "N" : "Y");
			
			return Optional.of(this.iamAuditTrailRepository.save(iamAuditTrail));
		}
		
		return Optional.empty();
	}
	
}
