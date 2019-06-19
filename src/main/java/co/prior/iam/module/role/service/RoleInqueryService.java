package co.prior.iam.module.role.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleInqueryService {


    RoleRepository roleRepository;
    SystemRepository systemRepository;

    public RoleInqueryService(RoleRepository roleRepository , SystemRepository systemRepository){

        this.roleRepository = roleRepository;
        this.systemRepository = systemRepository;
    }

    public List<IamMsRole> inqueryRole(Long systemId) throws Exception {


        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(systemId,"N")
                .orElseThrow(()-> new Exception("data not found"));

        List<IamMsRole> roleList = roleRepository.findByIamMsSystemAndIsDeleted(iamMsSystem, "N");
        if (!roleList.isEmpty()) {


            return roleList;
        }
        throw new Exception("data not found");
    }

}
