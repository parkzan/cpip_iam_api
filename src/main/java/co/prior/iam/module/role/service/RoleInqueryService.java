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

    public Optional<List<IamMsRole>> inqueryRole(Long systemId) throws Exception {


        Optional<List<IamMsRole>> roleList = roleRepository.findBySystemIdAndIsDeleted(systemId, "N");
        if (roleList.isPresent()) {


            return roleList;
        }
        return null;
    }

}
