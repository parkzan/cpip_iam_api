package co.prior.iam.module.role.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.role.model.request.RoleMapObjectReq;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleMapObjectService {

	private final RoleObjectRepository roleObjectRepository;
	private final RoleRepository roleRepository;
	private final ObjectRepository objectRepository;

	public RoleMapObjectService(RoleObjectRepository roleObjectRepository, RoleRepository roleRepository, 
			ObjectRepository objectRepository) {

		this.roleObjectRepository = roleObjectRepository;
		this.roleRepository = roleRepository;
		this.objectRepository = objectRepository;
	}

	@Transactional
	public void editRoleObject(RoleMapObjectReq roleMapObjectReq) {
		log.info("Service editRoleObject: {}", roleMapObjectReq);

		List<IamMsRoleObject> objectsList = this.roleObjectRepository.findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIsDeleted(
				roleMapObjectReq.getSystemId(), roleMapObjectReq.getRoleId(), AnswerFlag.N.toString());

		if (!objectsList.isEmpty()) {
			for (IamMsRoleObject object : objectsList) {
				if (!roleMapObjectReq.getNewObjectId().contains(object.getIamMsObject().getObjectId())) {
					object.setIsDeleted(AnswerFlag.Y.toString());
					roleObjectRepository.save(object);
				}
			}

			for (long newObj : roleMapObjectReq.getNewObjectId()) {
				Optional<IamMsRoleObject> iamMsObject = this.roleObjectRepository
						.findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIamMsObject_ObjectIdAndIsDeleted(
								objectsList.get(0).getIamMsSystem().getSystemId(),
								objectsList.get(0).getIamMsRole().getRoleId(), newObj, AnswerFlag.N.toString());

				if(!iamMsObject.isPresent()){
					IamMsObject newIamObject = this.objectRepository.findByIamMsSystem_SystemIdAndObjectIdAndIsDeleted(
							objectsList.get(0).getIamMsSystem().getSystemId(), newObj, AnswerFlag.N.toString())
							.orElseThrow(() -> new DataNotFoundException(ErrorCode.OBJECT_NOT_FOUND));
					if(newIamObject.getObjectType() == null){
						throw new DataNotFoundException(ErrorCode.DATA_INVALID);
					}

					IamMsRoleObject model = new IamMsRoleObject();
					model.setIamMsObject(newIamObject);
					model.setIamMsSystem(newIamObject.getIamMsSystem());
					model.setIamMsRole(objectsList.get(0).getIamMsRole());
					this.roleObjectRepository.save(model);
				}
			}
			
		} else if (roleMapObjectReq.getNewObjectId() != null) {
			IamMsRole iamMsRole = roleRepository.findByRoleIdAndIsDeleted(roleMapObjectReq.getRoleId(), AnswerFlag.N.toString())
					.orElseThrow(() -> new DataNotFoundException(ErrorCode.ROLE_NOT_FOUND));
			
			for (long newObj : roleMapObjectReq.getNewObjectId()) {
				IamMsObject iamMsObject = this.objectRepository.findByObjectIdAndIsDeleted(newObj, AnswerFlag.N.toString())
						.orElseThrow(() -> new DataNotFoundException(ErrorCode.OBJECT_NOT_FOUND));

				if(iamMsObject.getObjectType() == null){
					throw new DataNotFoundException(ErrorCode.DATA_INVALID);
				}
				IamMsRoleObject newModel = new IamMsRoleObject();
				newModel.setIamMsRole(iamMsRole);
				newModel.setIamMsSystem(iamMsRole.getIamMsSystem());
				newModel.setIamMsObject(iamMsObject);
				this.roleObjectRepository.save(newModel);
			}
		}
	}

}
