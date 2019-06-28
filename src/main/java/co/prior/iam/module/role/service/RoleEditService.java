package co.prior.iam.module.role.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.role.model.request.RoleEditReq;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleEditService {

	RoleRepository roleRepository;
	SystemRepository systemRepository;

	public RoleEditService(RoleRepository roleRepository, SystemRepository systemRepository) {

		this.roleRepository = roleRepository;
		this.systemRepository = systemRepository;

	}

	@Transactional
	public void editRole(RoleEditReq roleEditReq) {
		log.info("Service editRole: {}", roleEditReq);

		IamMsRole iamMsRole = roleRepository
				.findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(roleEditReq.getRoleCode(), roleEditReq.getSystemId(),
						AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException("data not found"));

		iamMsRole.setRoleName(roleEditReq.getNewName());
		iamMsRole.setRoleIcon(roleEditReq.getNewIcon());
		roleRepository.save(iamMsRole);
	}

}
