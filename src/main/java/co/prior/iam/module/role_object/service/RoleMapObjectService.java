package co.prior.iam.module.role_object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.module.role_object.model.request.RoleMapObjectReq;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleMapObjectService {

    RoleObjectRepository roleObjectRepository;
    RoleRepository roleRepository;
    SystemRepository systemRepository;
    ObjectRepository objectRepository;


    public RoleMapObjectService(RoleObjectRepository roleObjectRepository , RoleRepository roleRepository, SystemRepository systemRepository , ObjectRepository objectRepository) {


        this.roleObjectRepository = roleObjectRepository;
        this.roleRepository = roleRepository ;
        this.objectRepository = objectRepository;
        this.systemRepository = systemRepository;

    }

    @Transactional
    public void editRoleObject(RoleMapObjectReq roleMapObjectReq) throws Exception {
        log.info("Service editRoleObject: {}", roleMapObjectReq);
        IamMsRole iamMsRole = roleRepository.findByRoleIdAndIsDeleted(roleMapObjectReq.getRoleId(),"N")
                .orElseThrow(() -> new DataNotFoundException("data not found"));

        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(roleMapObjectReq.getSystemId() , "N")
                .orElseThrow(() -> new DataNotFoundException("data not found"));

        List<IamMsRoleObject> objectsList = roleObjectRepository.findByIamMsRoleAndIsDeleted(iamMsRole,"N");

        objectsList.removeAll(roleMapObjectReq.getNewObjectId());
        if(!objectsList.isEmpty()){

                 for (IamMsRoleObject obj: objectsList){
                     IamMsRoleObject iamMsRoleObject = roleObjectRepository.findByIamMsRoleAndIamMsObjectAndIsDeleted(iamMsRole,obj.getIamMsObject(),"N")
                             .orElseThrow(() -> new DataNotFoundException("data not found"));

                     iamMsRoleObject.setIsDeleted("Y");

                     roleObjectRepository.save(iamMsRoleObject);
                 }
        }


        if(roleMapObjectReq.getNewObjectId()!=null){

        for (Long newObj : roleMapObjectReq.getNewObjectId()){
            log.debug("team " + newObj.toString());
            IamMsObject iamMsObject = objectRepository.findByObjectIdAndIsDeleted(newObj,"N")
                    .orElseThrow(() -> new DataNotFoundException("data not found"));
            Optional<IamMsRoleObject> iamMsRoleObject = roleObjectRepository.findByIamMsRoleAndIamMsObjectAndIsDeleted(iamMsRole, iamMsObject , "N");

            if (!iamMsRoleObject.isPresent()){
                IamMsRoleObject newModel = new IamMsRoleObject();
                newModel.setIamMsRole(iamMsRole);
                newModel.setIamMsSystem(iamMsSystem);
                newModel.setIamMsObject(iamMsObject);

                roleObjectRepository.save(newModel);
            }


        }
    }

    }
}