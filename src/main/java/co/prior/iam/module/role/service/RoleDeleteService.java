package co.prior.iam.module.role.service;

import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsUserRole;
import co.prior.iam.repository.IamMsUserRoleRepository;
import co.prior.iam.repository.RoleObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
public class RoleDeleteService {

	private final RoleRepository roleRepository;
	private final IamMsUserRoleRepository iamMsUserRoleRepository;
	private final RoleObjectRepository roleObjectRepository;

	public RoleDeleteService(RoleRepository roleRepository, IamMsUserRoleRepository iamMsUserRoleRepository, RoleObjectRepository roleObjectRepository) {
		this.roleRepository = roleRepository;
		this.iamMsUserRoleRepository = iamMsUserRoleRepository;
		this.roleObjectRepository = roleObjectRepository;
	}

	@Transactional
	public void deleteRole(RoleDeleteReq roleDeleteReq) {
		log.info("Service deleteRole: {}", roleDeleteReq);

		IamMsRole iamMsRole = this.roleRepository.findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(
				roleDeleteReq.getRoleCode(), roleDeleteReq.getSystemId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND));

		List<IamMsUserRole> iamMsUserRole = this.iamMsUserRoleRepository.findByIamMsRole_RoleIdAndIsDeleted(iamMsRole.getRoleId(),"N");

		List<IamMsRoleObject> iamMsRoleObjects =this.roleObjectRepository.findByIamMsRole_RoleIdAndIsDeleted(iamMsRole.getRoleId(),"N");

		if (!iamMsUserRole.isEmpty()){
			for (IamMsUserRole userRole : iamMsUserRole){
				userRole.setIsDeleted("Y");
				this.iamMsUserRoleRepository.save(userRole);
			}
		}

		if (!iamMsRoleObjects.isEmpty()){
			for (IamMsRoleObject roleObject : iamMsRoleObjects){
				roleObject.setIsDeleted("Y");
				this.roleObjectRepository.save(roleObject);
			}
		}
		iamMsRole.setIsDeleted(AnswerFlag.Y.toString());
		this.roleRepository.save(iamMsRole);
	}
	
}
