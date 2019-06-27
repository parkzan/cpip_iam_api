package co.prior.iam.module.role.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.role.model.request.RoleMapObjectReq;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleMapObjectService {

	RoleObjectRepository roleObjectRepository;
	RoleRepository roleRepository;
	SystemRepository systemRepository;
	ObjectRepository objectRepository;

	public RoleMapObjectService(RoleObjectRepository roleObjectRepository, RoleRepository roleRepository,
			SystemRepository systemRepository, ObjectRepository objectRepository) {

		this.roleObjectRepository = roleObjectRepository;
		this.roleRepository = roleRepository;
		this.objectRepository = objectRepository;
		this.systemRepository = systemRepository;

	}

	@Transactional
	public void editRoleObject(RoleMapObjectReq roleMapObjectReq) throws Exception {
		log.info("Service editRoleObject: {}", roleMapObjectReq);

		List<IamMsRoleObject> objectsList = roleObjectRepository
				.findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIsDeleted(roleMapObjectReq.getSystemId(),
						roleMapObjectReq.getRoleId(), AnswerFlag.N.toString());

		if (!objectsList.isEmpty()) {

			for (IamMsRoleObject object : objectsList) {
				if (!roleMapObjectReq.getNewObjectId().contains(object.getIamMsObject().getObjectId())) {
					object.setIsDeleted(AnswerFlag.Y.toString());
					roleObjectRepository.save(object);
				}

			}

			for (long newObj : roleMapObjectReq.getNewObjectId()) {
				IamMsRoleObject iamMsObject = roleObjectRepository
						.findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIamMsObject_ObjectIdAndIsDeleted(
								objectsList.get(0).getIamMsSystem().getSystemId(),
								objectsList.get(0).getIamMsRole().getRoleId(), newObj, AnswerFlag.N.toString())
						.orElseThrow(() -> new DataNotFoundException("data not found"));
				if (!objectsList.contains(iamMsObject)) {
					IamMsRoleObject model = new IamMsRoleObject();
					model.setIamMsObject(iamMsObject.getIamMsObject());
					model.setIamMsSystem(iamMsObject.getIamMsSystem());
					model.setIamMsRole(objectsList.get(0).getIamMsRole());
					roleObjectRepository.save(model);
				}

			}

		} else if (roleMapObjectReq.getNewObjectId() != null) {
			IamMsRole iamMsRole = roleRepository
					.findByRoleIdAndIsDeleted(roleMapObjectReq.getRoleId(), AnswerFlag.N.toString())
					.orElseThrow(() -> new DataNotFoundException("data not found"));
			for (Long newObj : roleMapObjectReq.getNewObjectId()) {

				IamMsObject iamMsObject = objectRepository.findByObjectIdAndIsDeleted(newObj, AnswerFlag.N.toString())
						.orElseThrow(() -> new DataNotFoundException("data not found"));

				IamMsRoleObject newModel = new IamMsRoleObject();
				newModel.setIamMsRole(iamMsRole);
				newModel.setIamMsSystem(iamMsRole.getIamMsSystem());
				newModel.setIamMsObject(iamMsObject);

				roleObjectRepository.save(newModel);
			}
		}
	}

}
