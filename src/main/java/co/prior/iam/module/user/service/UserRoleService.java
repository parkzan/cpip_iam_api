package co.prior.iam.module.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.entity.IamMsUser;
import co.prior.iam.entity.IamMsUserRole;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.user.model.request.CreateUserRoleRequest;
import co.prior.iam.module.user.model.response.GetRoleUsersResponse;
import co.prior.iam.module.user.model.response.GetUserRolesResponse;
import co.prior.iam.module.user.model.response.UserData;
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
	
	public GetUserRolesResponse getUserRoles(long userId) {
		log.info("Service getUserRoles userId: {}", userId);
		
		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(
				userId, AnswerFlag.N.toString());
		if (iamMsUserRoles.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
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
		IamMsUser iamMsUser = iamMsUserRole.getIamMsUser();
		return GetUserRolesResponse.builder()
				.userRoleId(iamMsUserRole.getUserRoleId())
				.systemId(iamMsSystem.getSystemId())
				.systemCode(iamMsSystem.getSystemCode())
				.systemName(iamMsSystem.getSystemName())
				.userId(userId)
				.userCode(iamMsUser.getUserCode())
				.localFirstName(iamMsUser.getLocalFirstName())
				.localMiddleName(iamMsUser.getLocalMiddleName())
				.localLastName(iamMsUser.getLocalLastName())
				.engFirstName(iamMsUser.getEngFirstName())
				.engMiddleName(iamMsUser.getEngMiddleName())
				.engLastName(iamMsUser.getEngLastName())
				.userRoles(userRoles)
				.build();
	}
	
	public GetRoleUsersResponse getRoleUsers(long roleId) {
		log.info("Service getRoleUsers roleId: {}", roleId);
		
		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsRole_RoleIdAndIsDeleted(
				roleId, AnswerFlag.N.toString());
		if (iamMsUserRoles.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
		}
		
		List<UserData> users = new ArrayList<>();
		for (IamMsUserRole iamMsUserRole : iamMsUserRoles) {
			IamMsUser iamMsUser = iamMsUserRole.getIamMsUser();
			users.add(UserData.builder()
					.userId(iamMsUser.getUserId())
					.userCode(iamMsUser.getUserCode())
					.localFirstName(iamMsUser.getLocalFirstName())
					.localMiddleName(iamMsUser.getLocalMiddleName())
					.localLastName(iamMsUser.getLocalLastName())
					.engFirstName(iamMsUser.getEngFirstName())
					.engMiddleName(iamMsUser.getEngMiddleName())
					.engLastName(iamMsUser.getEngLastName())
					.build());
		}
		
		IamMsUserRole iamMsUserRole = iamMsUserRoles.get(0);
		IamMsSystem iamMsSystem = iamMsUserRole.getIamMsSystem();
		IamMsRole iamMsRole = iamMsUserRole.getIamMsRole();
		return GetRoleUsersResponse.builder()
				.userRoleId(iamMsUserRole.getUserRoleId())
				.systemId(iamMsSystem.getSystemId())
				.systemCode(iamMsSystem.getSystemCode())
				.systemName(iamMsSystem.getSystemName())
				.roleId(roleId)
				.roleCode(iamMsRole.getRoleCode())
				.roleName(iamMsRole.getRoleName())
				.users(users)
				.build();
	}
	
	@Transactional
	public IamMsUserRole createUserRole(CreateUserRoleRequest request) {
		log.info("Service createUserRole userId: {}", request.getUserId());
		
		long systemId = request.getSystemId();
		long userId = request.getUserId();
		long roleId = request.getRoleId();
		
		if (this.iamMsUserRoleRepository.existsByIamMsSystem_SystemIdAndIamMsUser_UserIdAndIamMsRole_RoleIdAndIsDeleted(
				systemId, userId, roleId, AnswerFlag.N.toString())) {
			
			throw new DataDuplicateException(ErrorCode.USER_ROLE_DUPLICATED);
		}
		
		IamMsSystem iamMsSystem = this.iamMsSystemRepository.findBySystemIdAndIsDeleted(systemId, AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.SYSTEM_NOT_FOUND));
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(userId, AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
		IamMsRole iamMsRole = this.iamMsRoleRepository.findByRoleIdAndIsDeleted(roleId, AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND));
		
		IamMsUserRole iamMsUserRole = IamMsUserRole.builder()
				.iamMsSystem(iamMsSystem)
				.iamMsUser(iamMsUser)
				.iamMsRole(iamMsRole)
				.build();
		
		return this.iamMsUserRoleRepository.save(iamMsUserRole);
	}
	
	@Transactional
	public void deleteUserRole(long userRoleId) {
		log.info("Service deleteUserRole userRoleId: {}", userRoleId);
		
		IamMsUserRole iamMsUserRole = this.iamMsUserRoleRepository.findByUserRoleIdAndIsDeleted(userRoleId, AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND));
		
		iamMsUserRole.setIsDeleted(AnswerFlag.Y.toString());
		this.iamMsUserRoleRepository.save(iamMsUserRole);
	}
	
}
