package co.prior.iam.module.role.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.role.model.request.RoleCreateReq;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleCreateService {

	RoleRepository roleRepository;

	SystemRepository systemRepository;

	public RoleCreateService(RoleRepository roleRepository, SystemRepository systemRepository) {
		this.roleRepository = roleRepository;
		this.systemRepository = systemRepository;

	}

	@Transactional
	public void createRole(RoleCreateReq roleCreateReq) {
		log.info("Service createRole: {}", roleCreateReq);
		
		IamMsSystem iamMsSystem = systemRepository
				.findBySystemIdAndIsDeleted(roleCreateReq.getSystemId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException("data not found"));

		Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndIamMsSystemAndIsDeleted(
				roleCreateReq.getRoleCode(), iamMsSystem, AnswerFlag.N.toString());

		if (!iamMsRole.isPresent()) {
			IamMsRole model = new IamMsRole();
			model.setRoleCode(roleCreateReq.getRoleCode());
			model.setRoleName(roleCreateReq.getRoleName());
			model.setRoleIcon(roleCreateReq.getRoleIcon());
			model.setIamMsSystem(iamMsSystem);
			roleRepository.save(model);

            }else {
                throw new DataDuplicateException("99","Role code duplicate");
            }


	}
}
