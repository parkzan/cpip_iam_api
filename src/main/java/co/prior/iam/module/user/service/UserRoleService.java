package co.prior.iam.module.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.entity.IamMsUser;
import co.prior.iam.entity.IamMsUserRole;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.user.model.request.CreateUserRoleRequest;
import co.prior.iam.module.user.model.response.GetUserRolesResponse;
import co.prior.iam.module.user.model.response.UserRole;
import co.prior.iam.repository.IamMsUserRepository;
import co.prior.iam.repository.IamMsUserRoleRepository;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserRoleService {

	private final SystemRepository iamMsSystemRepository;
	private final IamMsUserRepository iamMsUserRepository;
	private final RoleRepository iamMsRoleRepository;
	private final IamMsUserRoleRepository iamMsUserRoleRepository;
	
	public UserRoleService(SystemRepository iamMsSystemRepository, IamMsUserRepository iamMsUserRepository, 
			RoleRepository iamMsRoleRepository, IamMsUserRoleRepository iamMsUserRoleRepository) {
		
		this.iamMsSystemRepository = iamMsSystemRepository;
		this.iamMsUserRepository = iamMsUserRepository;
		this.iamMsRoleRepository = iamMsRoleRepository;
		this.iamMsUserRoleRepository = iamMsUserRoleRepository;
	}
	
	public GetUserRolesResponse getUserRoles(long userId) throws Exception {
		log.info("Service getUserRoles userId: {}", userId);
		
		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(userId, AnswerFlag.N.toString());
		if (iamMsUserRoles.isEmpty()) {
			throw new Exception("user role not found");
		}
		
		List<UserRole> userRoles = new ArrayList<>();
		for (IamMsUserRole iamMsUserRole : iamMsUserRoles) {
			IamMsRole iamMsRole = iamMsUserRole.getIamMsRole();
			userRoles.add(UserRole.builder()
					.roleId(iamMsRole.getRoleId())
					.roleCode(iamMsRole.getRoleCode())
					.roleName(iamMsRole.getRoleName())
					.build());
		}
		
		IamMsUserRole iamMsUserRole = iamMsUserRoles.get(0);
		IamMsSystem iamMsSystem = iamMsUserRole.getIamMsSystem();
		return GetUserRolesResponse.builder()
				.userRoleId(iamMsUserRole.getUserRoleId())
				.systemId(iamMsSystem.getSystemId())
				.systemCode(iamMsSystem.getSystemCode())
				.systemName(iamMsSystem.getSystemName())
				.userId(userId)
				.userCode(iamMsUserRole.getIamMsUser().getUserCode())
				.userRoles(userRoles)
				.build();
	}
	
	@Transactional
	public IamMsUserRole createUserRole(CreateUserRoleRequest request) throws Exception {
		log.info("Service createUserRole userId: {}", request.getUserId());
		
		long systemId = request.getSystemId();
		long userId = request.getUserId();
		long roleId = request.getRoleId();
		
		if (this.iamMsUserRoleRepository.existsByIamMsSystem_SystemIdAndIamMsUser_UserIdAndIamMsRole_RoleIdAndIsDeleted(
				systemId, userId, roleId, AnswerFlag.N.toString())) {
			
			throw new Exception("user role is duplicated");
		}
		
		IamMsSystem iamMsSystem = this.iamMsSystemRepository.findBySystemIdAndIsDeleted(systemId, AnswerFlag.N.toString())
				.orElseThrow(() -> new Exception("system not found"));
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, AnswerFlag.N.toString())
				.orElseThrow(() -> new Exception("user not found"));
		IamMsRole iamMsRole = this.iamMsRoleRepository.findByRoleIdAndIsDeleted(roleId, AnswerFlag.N.toString())
				.orElseThrow(() -> new Exception("role not found"));
		
		IamMsUserRole iamMsUserRole = IamMsUserRole.builder()
				.iamMsSystem(iamMsSystem)
				.iamMsUser(iamMsUser)
				.iamMsRole(iamMsRole)
				.build();
		
		return this.iamMsUserRoleRepository.save(iamMsUserRole);
	}
	
	@Transactional
	public void deleteUserRole(long userRoleId) throws Exception {
		log.info("Service deleteUserRole userRoleId: {}", userRoleId);
		
		IamMsUserRole iamMsUserRole = this.iamMsUserRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, AnswerFlag.N.toString())
				.orElseThrow(() -> new Exception("user role not found"));
		
		iamMsUserRole.setIsDeleted(AnswerFlag.Y.toString());
		this.iamMsUserRoleRepository.save(iamMsUserRole);
	}
	
}