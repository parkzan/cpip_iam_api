package co.prior.iam.module.role_object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.role_object.model.request.RoleObjectEditReq;
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
public class RoleObjectEditService {

    RoleObjectRepository roleObjectRepository;
    RoleRepository roleRepository;
    SystemRepository systemRepository;
    ObjectRepository objectRepository;


    public RoleObjectEditService(RoleObjectRepository roleObjectRepository , RoleRepository roleRepository,SystemRepository systemRepository , ObjectRepository objectRepository) {


        this.roleObjectRepository = roleObjectRepository;
        this.roleRepository = roleRepository ;
        this.objectRepository = objectRepository;
        this.systemRepository = systemRepository;

    }

    @Transactional
    public void editRoleObject(RoleObjectEditReq roleObjectEditReq) throws Exception {
        log.info("Service editRoleObject: {}", roleObjectEditReq);
        IamMsRole iamMsRole = roleRepository.findByRoleIdAndIsDeleted(roleObjectEditReq.getRoleId(),"N")
                .orElseThrow(() -> new Exception("data not found"));

        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(roleObjectEditReq.getSystemId() , "N")
                .orElseThrow(() -> new Exception("data not found"));

        List<IamMsRoleObject> objectsList = roleObjectRepository.findByIamMsRoleAndIsDeleted(iamMsRole,"N");

        if (!objectsList.isEmpty()){
            for (IamMsRoleObject obj : objectsList) {


                IamMsRoleObject iamMsRoleObject = roleObjectRepository.findByIamMsRoleAndIamMsObject(iamMsRole,obj.getIamMsObject())
                        .orElseThrow(() -> new Exception("data not found"));

                iamMsRoleObject.setIsDeleted("Y");

                roleObjectRepository.save(iamMsRoleObject);

            }
        }

        if(roleObjectEditReq.getNewObjectId()!=null){
        for (Long newObj : roleObjectEditReq.getNewObjectId()){
            IamMsObject iamMsObject = objectRepository.findByObjectIdAndIsDeleted(newObj,"N")
                    .orElseThrow(() -> new Exception("data not found"));
            Optional<IamMsRoleObject> iamMsRoleObject = roleObjectRepository.findByIamMsRoleAndIamMsObject(iamMsRole, iamMsObject);

            if (iamMsRoleObject.isPresent()){
                if (iamMsRoleObject.get().getIsDeleted().equals("Y")){

                    iamMsRoleObject.get().setIsDeleted("N");

                    roleObjectRepository.save(iamMsRoleObject.get());
                }
            }
            else {
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