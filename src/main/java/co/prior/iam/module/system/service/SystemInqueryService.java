package co.prior.iam.module.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SystemInqueryService {

	SystemRepository systemRepository;

	public SystemInqueryService(SystemRepository systemRepository) {
		this.systemRepository = systemRepository;
	}

	public List<IamMsSystem> inquerySystem() throws Exception {
		log.info("Service inquerySystem: {}");

		List<IamMsSystem> iamMsSystemList = systemRepository.findByIsDeleted(AnswerFlag.N.toString());

		if (!iamMsSystemList.isEmpty()) {

			return iamMsSystemList;
		}
		throw new DataNotFoundException("data not found");
	}
	
}
