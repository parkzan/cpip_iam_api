package co.prior.iam.module.role.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleDeleteService {

	private final RoleRepository roleRepository;

	public RoleDeleteService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Transactional
	public void deleteRole(RoleDeleteReq roleDeleteReq) {
		log.info("Service deleteRole: {}", roleDeleteReq);

		IamMsRole iamMsRole = this.roleRepository.findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(
				roleDeleteReq.getRoleCode(), roleDeleteReq.getSystemId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND));

		iamMsRole.setIsDeleted(AnswerFlag.Y.toString());
		this.roleRepository.save(iamMsRole);
	}
	
}
