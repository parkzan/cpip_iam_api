package co.prior.iam.module.system.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.system.model.request.SystemAddReq;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemCreateService {

	private final SystemRepository systemRepository;

	public SystemCreateService(SystemRepository systemRepository) {
		this.systemRepository = systemRepository;
	}

	@Transactional
	public IamMsSystem createSystem(SystemAddReq systemAddReq) {
		log.info("Service createSystem: {}", systemAddReq);

		Optional<IamMsSystem> check = this.systemRepository.findBySystemCodeAndIsDeleted(
				systemAddReq.getSystemCode(), AnswerFlag.N.toString());

		if (check.isPresent()) {
			throw new DataDuplicateException(ErrorCode.SYSTEM_DUPLICATED);	
		}

		IamMsSystem iamMsSystem = new IamMsSystem();
		iamMsSystem.setSystemCode(systemAddReq.getSystemCode());
		iamMsSystem.setSystemIcon(systemAddReq.getSystemIcon());
		iamMsSystem.setSystemName(systemAddReq.getSystemName());
		iamMsSystem.setUpdatedBy(null);
		iamMsSystem.setUpdatedDate(null);
		this.systemRepository.save(iamMsSystem);

		return  iamMsSystem;
    }

}
