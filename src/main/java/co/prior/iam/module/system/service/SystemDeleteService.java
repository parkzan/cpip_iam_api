package co.prior.iam.module.system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.system.model.request.SystemDeleteReq;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemDeleteService {

	private final SystemRepository systemRepository;

	public SystemDeleteService(SystemRepository systemRepository) {
		this.systemRepository = systemRepository;
	}

	@Transactional
	public void deleteSystem(SystemDeleteReq systemDeleteReq) {
		log.info("Service deleteSystem: {}", systemDeleteReq);

		IamMsSystem iamMsSystem = this.systemRepository.findBySystemCodeAndIsDeleted(
				systemDeleteReq.getSystemCode(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.SYSTEM_NOT_FOUND));

		iamMsSystem.setIsDeleted(AnswerFlag.Y.toString());

		this.systemRepository.save(iamMsSystem);
	}

}
