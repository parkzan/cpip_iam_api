package co.prior.iam.module.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.entity.IamMsUserRole;
import co.prior.iam.module.user.model.response.GetUserRolesResponse;
import co.prior.iam.module.user.model.response.UserRole;
import co.prior.iam.repository.IamMsUserRoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserRoleService {

	private final IamMsUserRoleRepository iamMsUserRoleRepository;
	
	public UserRoleService(IamMsUserRoleRepository iamMsUserRoleRepository) {
		this.iamMsUserRoleRepository = iamMsUserRoleRepository;
	}
	
	public GetUserRolesResponse getUserRoles(long userId) throws Exception {
		log.info("Service getUserRoles userId: {}", userId);
		
		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(userId, "N");
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
	public void deleteUserRole(long userRoleId) throws Exception {
		log.info("Service deleteUserRole userRoleId: {}", userRoleId);
		
		IamMsUserRole iamMsUserRole = this.iamMsUserRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, "N")
				.orElseThrow(() -> new Exception("user role not found"));
		
		iamMsUserRole.setIsDeleted("Y");
		this.iamMsUserRoleRepository.save(iamMsUserRole);
	}
	
}
