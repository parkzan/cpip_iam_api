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


        List<IamMsRoleObject> objectsList = roleObjectRepository.findByIamMsSystem_SystemIdAndIamMsRole_RoleIdAndIsDeleted(roleMapObjectReq.getSystemId(),roleMapObjectReq.getRoleId(),"N");

        if(!objectsList.isEmpty()){

            for (IamMsRoleObject object : objectsList){
                if (!roleMapObjectReq.getNewObjectId().contains(object.getIamMsObject().getObjectId())){

                        object.setIsDeleted("Y");
                        roleObjectRepository.save(object);
                }



            }

            for (long newObj:roleMapObjectReq.getNewObjectId()){

                IamMsObject iamMsObject = objectRepository.findByObjectIdAndIsDeleted(newObj,"N")
                        .orElseThrow(() -> new DataNotFoundException("data not found"));
                if (!objectsList.contains(iamMsObject)){
                    IamMsRoleObject model = new IamMsRoleObject();
                    model.setIamMsObject(iamMsObject);
                    model.setIamMsSystem(iamMsObject.getIamMsSystem());
                    model.setIamMsRole(objectsList.get(0).getIamMsRole());
                    roleObjectRepository.save(model);
                }


            }


        }
        else if (roleMapObjectReq.getNewObjectId()!= null) {

            IamMsRole iamMsRole = roleRepository.findByRoleIdAndIsDeleted(roleMapObjectReq.getRoleId(),"N")
                    .orElseThrow(() -> new DataNotFoundException("data not found"));
            for (Long newObj : roleMapObjectReq.getNewObjectId()){

                IamMsObject iamMsObject = objectRepository.findByObjectIdAndIsDeleted(newObj,"N")
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