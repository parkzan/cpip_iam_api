package co.prior.iam.module.role_object.service;


import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.module.role_object.model.request.GetRoleObjectReq;
import co.prior.iam.repository.RoleObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetRoleObjectService {

    RoleObjectRepository roleObjectRepository;

    GetRoleObjectService(RoleObjectRepository roleObjectRepository){

        this.roleObjectRepository = roleObjectRepository ;
    }

    @Transactional
    public List<IamMsRoleObject> getRoleObject(Long roleId) throws Exception{

        List<IamMsRoleObject> iamMsRoleObjects = roleObjectRepository.findByRoleIdAndIsDeleted(roleId,"N");

        if(!iamMsRoleObjects.isEmpty()){

            return iamMsRoleObjects;
        }
        throw new Exception("data not found");
    }
}
