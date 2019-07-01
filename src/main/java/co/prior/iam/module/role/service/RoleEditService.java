package co.prior.iam.module.role.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.role.model.request.RoleEditReq;
import co.prior.iam.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleEditService {

	private final RoleRepository roleRepository;

	public RoleEditService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Transactional
	public void editRole(RoleEditReq roleEditReq) {
		log.info("Service editRole: {}", roleEditReq);

		IamMsRole iamMsRole = this.roleRepository.findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(
				roleEditReq.getRoleCode(), roleEditReq.getSystemId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND));

		iamMsRole.setRoleName(roleEditReq.getNewName());
		iamMsRole.setRoleIcon(roleEditReq.getNewIcon());
		this.roleRepository.save(iamMsRole);
	}

}
