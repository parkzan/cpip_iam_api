package co.prior.iam.module.system.service;

import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemEditReq;
import co.prior.iam.repository.SystemRepository;

@Slf4j
@Service
public class SystemEditService {

	SystemRepository systemRepository;

	public SystemEditService(SystemRepository systemRepository) {
		this.systemRepository = systemRepository;
	}

	@Transactional
	public void editSystem(SystemEditReq systemEditReq) throws Exception {
		log.info("Service editSystem: {}", systemEditReq);
		IamMsSystem iamMsSystem = systemRepository
				.findBySystemCodeAndIsDeleted(systemEditReq.getSystemCode(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException("data not found"));

		iamMsSystem.setSystemName(systemEditReq.getNewName());
		iamMsSystem.setSystemIcon(systemEditReq.getNewIcon());

		systemRepository.save(iamMsSystem);
	}

}
