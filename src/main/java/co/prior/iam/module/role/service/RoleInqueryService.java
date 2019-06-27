package co.prior.iam.module.role.service;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoleInqueryService {

	RoleRepository roleRepository;
	SystemRepository systemRepository;

	public RoleInqueryService(RoleRepository roleRepository, SystemRepository systemRepository) {
		this.roleRepository = roleRepository;
		this.systemRepository = systemRepository;
	}

	public List<IamMsRole> inqueryRole(Long systemId) throws Exception {
		log.info("Service inqueryRole: {}", systemId);

		List<IamMsRole> roleList = roleRepository.findByIamMsSystem_SystemIdAndIsDeleted(systemId,
				AnswerFlag.N.toString());
		if (!roleList.isEmpty()) {
			return roleList;
		}
		throw new DataNotFoundException("data not found");
	}

}
