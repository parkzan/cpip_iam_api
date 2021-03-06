package co.prior.iam.module.user.service;

import java.util.*;

import co.prior.iam.entity.*;
import co.prior.iam.model.ObjectType;
import co.prior.iam.module.param.model.response.ParamInfoData;
import co.prior.iam.module.param.service.ParamService;
import co.prior.iam.module.user.model.request.DeleteAllUserRoleInUserRequest;
import co.prior.iam.module.user.model.response.*;
import co.prior.iam.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.role.model.respone.ObjectModel;
import co.prior.iam.module.role.model.respone.RoleMapObjectRespone;
import co.prior.iam.module.role.service.GetRoleObjectService;
import co.prior.iam.module.user.model.request.CreateUserRoleRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserRoleService {

	private final GetRoleObjectService getRoleObjectService;
	private final SystemRepository iamMsSystemRepository;
	private final IamMsUserRepository iamMsUserRepository;
	private final RoleRepository iamMsRoleRepository;
	private final IamMsUserRoleRepository iamMsUserRoleRepository;
	private final ParamService paramService;

	public UserRoleService(GetRoleObjectService getRoleObjectService, SystemRepository iamMsSystemRepository, IamMsUserRepository iamMsUserRepository, RoleRepository iamMsRoleRepository, IamMsUserRoleRepository iamMsUserRoleRepository, ParamService paramService) {
		this.getRoleObjectService = getRoleObjectService;
		this.iamMsSystemRepository = iamMsSystemRepository;
		this.iamMsUserRepository = iamMsUserRepository;
		this.iamMsRoleRepository = iamMsRoleRepository;
		this.iamMsUserRoleRepository = iamMsUserRoleRepository;
		this.paramService = paramService;
	}

	public  List<GetUserRoleSystemRespone> getUserRolesBySystem(long systemId){
		log.info("Service getUserRoles systemId: {}", systemId);


		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsSystem_SystemIdAndIsDeleted(
				systemId, AnswerFlag.N.toString());

		List<GetUserRoleSystemRespone> list = new ArrayList<>();

		if (iamMsUserRoles.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
		}

		for (IamMsUserRole userRole : iamMsUserRoles){



			GetUserRoleSystemRespone respone = GetUserRoleSystemRespone.builder().roleCode(userRole.getIamMsRole().getRoleCode())
													.roleId(userRole.getIamMsRole().getRoleId())
													.userRoleId(userRole.getUserRoleId())
													.roleName(userRole.getIamMsRole().getRoleName())
													.userId(userRole.getIamMsUser().getUserId())
													.userCode(userRole.getIamMsUser().getUserCode())
													.engFirstName(userRole.getIamMsUser().getEngFirstName())
													.engLastName(userRole.getIamMsUser().getEngLastName())
													.engMiddleName(userRole.getIamMsUser().getEngMiddleName())
													.localFirstName(userRole.getIamMsUser().getLocalFirstName())
													.localLastName(userRole.getIamMsUser().getLocalLastName())
													.localMiddleName(userRole.getIamMsUser().getLocalMiddleName())
													.build();

			list.add(respone);
		}


		return  list;

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
					.userRoleId(iamMsUserRole.getUserRoleId())
					.roleId(iamMsRole.getRoleId())
					.roleCode(iamMsRole.getRoleCode())
					.roleName(iamMsRole.getRoleName())
					.build());
		}
		
		IamMsUserRole iamMsUserRole = iamMsUserRoles.get(0);
		IamMsSystem iamMsSystem = iamMsUserRole.getIamMsSystem();
		IamMsUser iamMsUser = iamMsUserRole.getIamMsUser();
		return GetUserRolesResponse.builder()
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

	public List<UserRoleSystemRespone>  getAllUserRoles(long userId) {
		log.info("Service getAllUserRoles userId: {}", userId);

//		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(
//				userId, AnswerFlag.N.toString());

		List<Long> listSystemId = iamMsUserRoleRepository.getListSystemId(userId);

		List<UserRoleSystemRespone> list = new ArrayList<>();





		if (listSystemId.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
		}



			for (Long systemId : listSystemId){

				List<IamMsUserRole> userRoles = iamMsUserRoleRepository.findByIamMsSystem_SystemIdAndIamMsUser_UserIdAndIsDeleted(systemId,userId,AnswerFlag.N.toString());
				if (userRoles.isEmpty()) {
					throw new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
				}
				List<UserRole> userRoleList = new ArrayList<>();
				for (IamMsUserRole user : userRoles) {

						UserRole userRoleModel = UserRole.builder().userRoleId(user.getUserRoleId())
								.roleCode(user.getIamMsRole().getRoleCode())
								.roleId(user.getIamMsRole().getRoleId())
								.roleName(user.getIamMsRole().getRoleName())
								.build();
						userRoleList.add(userRoleModel);


					}
				UserRoleSystemRespone respone = UserRoleSystemRespone.builder().systemCode(userRoles.get(0).getIamMsSystem().getSystemCode())
						.systemId(userRoles.get(0).getIamMsSystem().getSystemId())
						.systemName(userRoles.get(0).getIamMsSystem().getSystemName())
						.systemIcon(userRoles.get(0).getIamMsSystem().getSystemIcon())
						.userRoles(userRoleList)
						.build();


				list.add(respone);



			}



		return list;
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
					.userRoleId(iamMsUserRole.getUserRoleId())
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

	@Transactional
	public void deleteAllUserRole(DeleteAllUserRoleInUserRequest request) {
		log.info("Service deleteAllUserRole userRoleId: {}", request.getUserId());

		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(request.getUserId(), AnswerFlag.N.toString());
		if (iamMsUserRoles.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
		}
		for (IamMsUserRole userRole : iamMsUserRoles) {
			if (userRole.getIamMsSystem().getSystemId().equals(request.getSystemId())) {
				userRole.setIsDeleted(AnswerFlag.Y.toString());
				this.iamMsUserRoleRepository.save(userRole);
			}
		}
	}
	
	public List<UserRoleObject> getUserRoleObject(long userId) {
		log.info("Service getUserRoleObject userId: {}", userId);
		
		List<UserRoleObject> userRoleObjects = new ArrayList<>();
		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(
				userId, AnswerFlag.N.toString());
		
		for (IamMsUserRole userRole : iamMsUserRoles) {
			IamMsRole iamMsRole = userRole.getIamMsRole();
			List<UserObject> userObjects = new ArrayList<>();
			Optional<RoleMapObjectRespone> roleMapObjectOpt = this.getRoleObjectService.getRoleMapObject(iamMsRole.getRoleId());
			if (roleMapObjectOpt.isPresent()) {
				List<ObjectModel> roleObjects = roleMapObjectOpt.get().getObjects();
				this.addObjects(userObjects, roleObjects);
			}
			userRoleObjects.add(UserRoleObject.builder()
					.systemCode(iamMsRole.getIamMsSystem().getSystemCode())
					.roleCode(iamMsRole.getRoleCode())
					.objects(userObjects)
					.build());
		}

		
		return userRoleObjects;
	}

	public List<UserRoleObject> getMenuObject(long userId){

		List<UserRoleObject> userMenuRoleObjects = new ArrayList<>();
		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(
				userId, AnswerFlag.N.toString());

		for (IamMsUserRole iamMsUserRole : iamMsUserRoles){
			IamMsRole iamMsRole = iamMsUserRole.getIamMsRole();
			List<UserObject> userObjects = new ArrayList<>();
			Optional<RoleMapObjectRespone> roleMapObjectOpt = this.getRoleObjectService.getRoleMapObject(iamMsRole.getRoleId());

			if (roleMapObjectOpt.isPresent()) {
				List<ObjectModel> roleObjects = roleMapObjectOpt.get().getObjects();
				this.addMenuObjects(userObjects, roleObjects);
			}
			userMenuRoleObjects.add(UserRoleObject.builder()
					.systemCode(iamMsRole.getIamMsSystem().getSystemCode())
					.roleCode(iamMsRole.getRoleCode())
					.objects(userObjects)
					.build());

		}




		return userMenuRoleObjects;

	}
	public Set<String> getUserObject(long userId){


		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsUser_UserIdAndIsDeleted(
				userId, AnswerFlag.N.toString());

		Set<String> list = new HashSet<>();


		for (IamMsUserRole userRole : iamMsUserRoles) {
			IamMsRole iamMsRole = userRole.getIamMsRole();

			Optional<RoleMapObjectRespone> roleMapObjectOpt = this.getRoleObjectService.getRoleMapObject(iamMsRole.getRoleId());
			if (roleMapObjectOpt.isPresent()) {
				List<ObjectModel> roleObjects = roleMapObjectOpt.get().getObjects();
				this.addObject(list, roleObjects);
			}




		}




 return  list ;
	}


	
	private void addObjects(List<UserObject> userObjects, List<ObjectModel> roleObjects) {
		for (ObjectModel roleObject : roleObjects) {
			List<UserObject> objects = new ArrayList<>();
			List<ObjectModel> list = roleObject.getObjects();
			if (!list.isEmpty()) {
				this.addObjects(objects, list);
			}
			
			userObjects.add(UserObject.builder()
					.objectId(roleObject.getObjectId())
					.objectCode(roleObject.getObjectCode())
					.objectName(roleObject.getObjectName())
					.objectUrl(roleObject.getObjectUrl())
					.objectParentId(roleObject.getObjectParentId())
					.objects(objects)
					.build());
		}
	}

	private void addMenuObjects(List<UserObject> userObjects, List<ObjectModel> roleObjects) {
		log.info("role : {}",roleObjects );
		ParamInfoData objType = this.getObjectType(ObjectType.MENU);

		log.info("ObjectType : {}",objType.getParamInfoId() );
		for (ObjectModel roleObject : roleObjects) {
			List<UserObject> objects = new ArrayList<>();
			List<ObjectModel> list = roleObject.getObjects();
			if (!list.isEmpty()) {
				this.addMenuObjects(objects, list);
			}

				 if(roleObject.getObjectType() == objType.getParamInfoId()){


					 userObjects.add(UserObject.builder()
							 .objectId(roleObject.getObjectId())
							 .objectCode(roleObject.getObjectCode())
							 .objectName(roleObject.getObjectName())
							 .objectUrl(roleObject.getObjectUrl())
							 .objects(objects)
							 .objectParentId(roleObject.getObjectParentId())
							 .sort(roleObject.getSorting())
							 .build());
//
//					 if(roleObject.getSorting() != null)
//
				 }


		}
		userObjects.sort(Comparator.comparing(UserObject::getSort));


	}

	private void addObject(Set<String> lists, List<ObjectModel> roleObjects) {
		for (ObjectModel roleObject : roleObjects) {
//			List<String> objects = new ArrayList<>();
			List<ObjectModel> list = roleObject.getObjects();
			lists.add(roleObject.getObjectCode());
			if (!list.isEmpty()) {
				this.addObject(lists, list);
			}



		}
	}

	private ParamInfoData getObjectType(ObjectType value) {
		return this.paramService.getObjectType(value)
				.orElse(ParamInfoData.builder()
						.paramEnMessage("System error")
						.paramLocalMessage("System error")
						.build());
	}

	private void sortTreeObject(List<UserRoleObject> userRoleObjects){


	}

	public  List<GetUserRoleSystemRespone> getUserRolesBySystemCode(String systemCode){
		log.info("Service getUserRolesBySystemCode: {}", systemCode);


		List<IamMsUserRole> iamMsUserRoles = this.iamMsUserRoleRepository.findByIamMsSystem_SystemCodeAndIsDeleted(
				systemCode, AnswerFlag.N.toString());

		List<GetUserRoleSystemRespone> list = new ArrayList<>();

		if (iamMsUserRoles.isEmpty()) {
			throw new DataNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
		}

		for (IamMsUserRole userRole : iamMsUserRoles){



			GetUserRoleSystemRespone respone = GetUserRoleSystemRespone.builder().roleCode(userRole.getIamMsRole().getRoleCode())
					.roleId(userRole.getIamMsRole().getRoleId())
					.userRoleId(userRole.getUserRoleId())
					.roleName(userRole.getIamMsRole().getRoleName())
					.userId(userRole.getIamMsUser().getUserId())
					.userCode(userRole.getIamMsUser().getUserCode())
					.engFirstName(userRole.getIamMsUser().getEngFirstName())
					.engLastName(userRole.getIamMsUser().getEngLastName())
					.engMiddleName(userRole.getIamMsUser().getEngMiddleName())
					.localFirstName(userRole.getIamMsUser().getLocalFirstName())
					.localLastName(userRole.getIamMsUser().getLocalLastName())
					.localMiddleName(userRole.getIamMsUser().getLocalMiddleName())
					.build();

			list.add(respone);
		}


		return  list;

	}



}
