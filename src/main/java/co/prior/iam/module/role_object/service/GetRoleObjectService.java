package co.prior.iam.module.role_object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.module.role_object.model.respone.ObjectMapRespone;
import co.prior.iam.module.role_object.model.respone.ObjectModel;
import co.prior.iam.module.role_object.model.respone.RoleMapObjectRespone;
import co.prior.iam.module.role_object.model.respone.RoleMapRespone;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.RoleObjectRepository;
import co.prior.iam.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class GetRoleObjectService {

    RoleObjectRepository roleObjectRepository;
    RoleRepository roleRepository;
    ObjectRepository objectRepository;

    GetRoleObjectService(RoleObjectRepository roleObjectRepository,RoleRepository roleRepository , ObjectRepository objectRepository){

        this.roleObjectRepository = roleObjectRepository ;
        this.roleRepository = roleRepository;
        this.objectRepository = objectRepository;
    }

    @Transactional
    public RoleMapObjectRespone getRoleObject(Long roleId) throws Exception{

        IamMsRole iamMsRole = roleRepository.findByRoleIdAndIsDeleted(roleId,"N")
                .orElseThrow(() -> new Exception("data not found"));



        List<IamMsRoleObject> objectList = roleObjectRepository.findByIamMsRoleAndIsDeleted(iamMsRole,"N");


        for (IamMsRoleObject obj : objectList){

            if (obj.getIamMsObject().getObjectParentId() == null){

            }
        }



        RoleMapObjectRespone respone = new RoleMapObjectRespone();




        if(!objectList.isEmpty()){


            return respone ;






        }
        throw new Exception("data not found");
    }

    private List<ObjectModel> setObjectChild(IamMsRoleObject root){

        return null;
    }
}
