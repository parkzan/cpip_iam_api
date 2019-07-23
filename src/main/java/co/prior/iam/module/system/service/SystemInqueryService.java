package co.prior.iam.module.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemInqueryService {

	private final SystemRepository systemRepository;

	public SystemInqueryService(SystemRepository systemRepository) {
		this.systemRepository = systemRepository;
	}

	public List<IamMsSystem> inquerySystem() {
		log.info("Service inquerySystem");

		List<IamMsSystem> iamMsSystemList = this.systemRepository.findByIsDeletedOrderBySystemId(AnswerFlag.N.toString());

		if (iamMsSystemList.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.SYSTEM_NOT_FOUND);
		}
		
		return iamMsSystemList;
	}
	
}
