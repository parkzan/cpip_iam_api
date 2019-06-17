package co.prior.iam.module.role.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleInqueryService {

    @Autowired
    RoleRepository roleRepository;

    public List<IamMsRole> inqueryRole(Long systemId) throws Exception {


        List<IamMsRole> roleList = roleRepository.findBySystemIdAndIsDeleted(systemId, "N");
        if (!roleList.isEmpty()) {


            return roleList;
        }
        throw new Exception("data not found");
    }

}
