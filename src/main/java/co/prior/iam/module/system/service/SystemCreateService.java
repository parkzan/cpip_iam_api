package co.prior.iam.module.system.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.system.model.request.SystemAddReq;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemCreateService {

	SystemRepository systemRepository;

	public SystemCreateService(SystemRepository systemRepository) {

		this.systemRepository = systemRepository;
	}

	@Transactional
	public void createSystem(SystemAddReq systemAddReq) {
		log.info("Service createSystem: {}", systemAddReq);

		Optional<IamMsSystem> check = systemRepository.findBySystemCodeAndIsDeleted(systemAddReq.getSystemCode(),
				AnswerFlag.N.toString());

		if (!check.isPresent()) {
			IamMsSystem iamMsSystem = new IamMsSystem();
			iamMsSystem.setSystemCode(systemAddReq.getSystemCode());
			iamMsSystem.setSystemIcon(systemAddReq.getSystemIcon());
			iamMsSystem.setSystemName(systemAddReq.getSystemName());
			systemRepository.save(iamMsSystem);

             }
            else {

                 throw new DataDuplicateException("99","System code duplicate");

            }

    }

}

