package co.prior.iam.module.role_object.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.module.role_object.model.request.GetRoleObjectReq;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetRoleObjectService {

    RoleObjectRepository roleObjectRepository;
    RoleRepository roleRepository;

    GetRoleObjectService(RoleObjectRepository roleObjectRepository,RoleRepository roleRepository){

        this.roleObjectRepository = roleObjectRepository ;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public List<IamMsRoleObject> getRoleObject(Long roleId) throws Exception{

        IamMsRole iamMsRole = roleRepository.findByRoleIdAndIsDeleted(roleId,"N")
                .orElseThrow(() -> new Exception("data not found"));

        List<IamMsRoleObject> iamMsRoleObjects = roleObjectRepository.findByIamMsRoleAndIsDeleted(iamMsRole,"N");

        if(!iamMsRoleObjects.isEmpty()){

            return iamMsRoleObjects;
        }
        throw new Exception("data not found");
    }
}
