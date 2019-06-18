package co.prior.iam.module.role_object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.module.role_object.model.request.RoleMapObjectCreateReq;
import co.prior.iam.repository.RoleObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleObjectCreateService {

    RoleObjectRepository roleObjectRepository;


    RoleObjectCreateService(RoleObjectRepository roleObjectRepository){

        this.roleObjectRepository = roleObjectRepository;

    }

    @Transactional
    public void createRoleMapObject(RoleMapObjectCreateReq roleMapObjectCreateReq) throws Exception{

        for (Long obj: roleMapObjectCreateReq.getObjectId()) {
            Optional<IamMsRoleObject> iamMsRoleObject = roleObjectRepository.
                    findBySystemIdAndRoleIdAndObjectIdAndIsDeleted(roleMapObjectCreateReq.getSystemId(),
                            roleMapObjectCreateReq.getRoleId(),
                            obj,
                            "N");

            if(!iamMsRoleObject.isPresent()){
                IamMsRoleObject model = new IamMsRoleObject();
                model.setSystemId(roleMapObjectCreateReq.getSystemId());
                model.setRoleId(roleMapObjectCreateReq.getRoleId());
                model.setObjectId(obj);

                roleObjectRepository.save(model);
            }else throw new Exception("role and object user to map");


        }


    }
}
