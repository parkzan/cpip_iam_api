package co.prior.iam.module.role_object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.role_object.model.request.RoleMapObjectCreateReq;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleObjectCreateService {

    RoleObjectRepository roleObjectRepository;
    RoleRepository roleRepository;
    SystemRepository systemRepository;
    ObjectRepository objectRepository;

    RoleObjectCreateService(RoleObjectRepository roleObjectRepository , RoleRepository roleRepository , SystemRepository systemRepository , ObjectRepository objectRepository){

        this.roleObjectRepository = roleObjectRepository;
        this.roleRepository = roleRepository;
        this.systemRepository = systemRepository;
        this.objectRepository = objectRepository;

    }

    @Transactional
    public void createRoleMapObject(RoleMapObjectCreateReq roleMapObjectCreateReq) throws Exception{

        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(roleMapObjectCreateReq.getSystemId(),"N")
                .orElseThrow(() -> new Exception("data not found"));

        IamMsRole iamMsRole = roleRepository.findByRoleIdAndIsDeleted(roleMapObjectCreateReq.getRoleId(),"N")
                .orElseThrow(() -> new Exception("data not found"));


        for (Long obj: roleMapObjectCreateReq.getObjectId()) {
            IamMsObject iamMsObject = objectRepository.findByObjectIdAndIsDeleted(obj,"N")
                    .orElseThrow(() -> new Exception("data not found"));

            Optional<IamMsRoleObject> iamMsRoleObject = roleObjectRepository.
                    findByIamMsSystemAndIamMsRoleAndIamMsObjectAndIsDeleted(iamMsSystem,
                            iamMsRole,
                            iamMsObject,
                            "N");

            if(!iamMsRoleObject.isPresent()){
                IamMsRoleObject model = new IamMsRoleObject();
                model.setIamMsSystem(iamMsSystem);
                model.setIamMsRole(iamMsRole);
                model.setIamMsObject(iamMsObject);

                roleObjectRepository.save(model);
            }else throw new Exception("role and object user to map");


        }


    }
}
