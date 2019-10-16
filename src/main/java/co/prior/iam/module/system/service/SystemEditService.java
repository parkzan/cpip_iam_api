package co.prior.iam.module.system.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.repository.ObjectRepository;
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
	private final ObjectRepository objectRepository;


	public SystemEditService(SystemRepository systemRepository, ObjectRepository objectRepository) {
		this.systemRepository = systemRepository;
		this.objectRepository = objectRepository;
	}

	@Transactional
	public void editSystem(SystemEditReq systemEditReq) {
		log.info("Service editSystem: {}", systemEditReq);

		IamMsSystem iamMsSystem = this.systemRepository.findBySystemCodeAndIsDeleted(
				systemEditReq.getSystemCode(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.SYSTEM_NOT_FOUND));


		IamMsObject iamMsObject = this.objectRepository.findByIamMsSystem_SystemCodeAndObjectParentNullAndIsDeleted(systemEditReq.getSystemCode(),AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException((ErrorCode.OBJECT_NOT_FOUND)));

		iamMsSystem.setSystemName(systemEditReq.getNewName());
		iamMsSystem.setSystemIcon(systemEditReq.getNewIcon());

		this.systemRepository.save(iamMsSystem);

		iamMsObject.setObjectName(systemEditReq.getNewName());
		this.objectRepository.save(iamMsObject);
	}
}