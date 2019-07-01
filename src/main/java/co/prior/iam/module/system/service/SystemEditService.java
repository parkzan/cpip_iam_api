package co.prior.iam.module.system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.system.model.request.SystemEditReq;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemEditService {

	private final SystemRepository systemRepository;

	public SystemEditService(SystemRepository systemRepository) {
		this.systemRepository = systemRepository;
	}

	@Transactional
	public void editSystem(SystemEditReq systemEditReq) {
		log.info("Service editSystem: {}", systemEditReq);
		
		IamMsSystem iamMsSystem = this.systemRepository.findBySystemCodeAndIsDeleted(
				systemEditReq.getSystemCode(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.SYSTEM_NOT_FOUND));

		iamMsSystem.setSystemName(systemEditReq.getNewName());
		iamMsSystem.setSystemIcon(systemEditReq.getNewIcon());

		this.systemRepository.save(iamMsSystem);
	}

}
