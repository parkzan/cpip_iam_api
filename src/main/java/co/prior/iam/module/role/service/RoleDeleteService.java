package co.prior.iam.module.role.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleDeleteService {

	RoleRepository roleRepository;
	SystemRepository systemRepository;

	public RoleDeleteService(RoleRepository roleRepository, SystemRepository systemRepository) {

		this.roleRepository = roleRepository;
		this.systemRepository = systemRepository;
	}

	@Transactional
	public void deleteRole(RoleDeleteReq roleDeleteReq) {
		log.info("Service deleteRole: {}", roleDeleteReq);

		IamMsRole iamMsRole = roleRepository
				.findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(roleDeleteReq.getRoleCode(),
						roleDeleteReq.getSystemId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException("data not found"));

		iamMsRole.setIsDeleted(AnswerFlag.Y.toString());

		roleRepository.save(iamMsRole);
	}
}
