package co.prior.iam.module.role_object.service;


import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.module.role_object.model.request.RoleObjectEditReq;
import co.prior.iam.repository.RoleObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoleObjectEditService {

    RoleObjectRepository roleObjectRepository;


    public RoleObjectEditService(RoleObjectRepository roleObjectRepository) {


        this.roleObjectRepository = roleObjectRepository;

    }

    @Transactional
    public void editRoleObject(RoleObjectEditReq roleObjectEditReq) throws Exception {


        List<IamMsRoleObject> objectsList = roleObjectRepository.findByRoleIdAndIsDeleted(roleObjectEditReq.getRoleId(),"N");

        if (!objectsList.isEmpty()){
            for (IamMsRoleObject obj : objectsList) {

                IamMsRoleObject iamMsRoleObject = roleObjectRepository.findByRoleIdAndObjectId(roleObjectEditReq.getRoleId(),obj.getObjectId())
                        .orElseThrow(() -> new Exception("data not found"));

                iamMsRoleObject.setIsDeleted("Y");

                roleObjectRepository.save(iamMsRoleObject);

            }
        }

        if(roleObjectEditReq.getNewObjectId()!=null){
        for (Long newObj : roleObjectEditReq.getNewObjectId()){
            Optional<IamMsRoleObject> iamMsRoleObject = roleObjectRepository.findByRoleIdAndObjectId(roleObjectEditReq.getRoleId(), newObj);

            if (iamMsRoleObject.isPresent()){
                if (iamMsRoleObject.get().getIsDeleted().equals("Y")){

                    iamMsRoleObject.get().setIsDeleted("N");

                    roleObjectRepository.save(iamMsRoleObject.get());
                }
            }
            else {
                IamMsRoleObject newModel = new IamMsRoleObject();
                newModel.setRoleId(roleObjectEditReq.getRoleId());
                newModel.setSystemId(roleObjectEditReq.getSystemId());
                newModel.setObjectId(newObj);

                roleObjectRepository.save(newModel);
            }


        }
    }

    }
}