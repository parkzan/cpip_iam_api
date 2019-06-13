package co.prior.iam.module.role.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.request.RoleEditReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleEditService {

    @Autowired
    RoleRepository roleRepository;


    @Transactional
    public void editRole(RoleEditReq roleEditReq) throws Exception{


                Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleEditReq.getRoleCode(),roleEditReq.getSystemId(),"N");

                if(iamMsRole.isPresent()) {


                    iamMsRole.get().setRoleName(roleEditReq.getNewName());
                    iamMsRole.get().setRoleIcon(roleEditReq.getNewIcon());
                    roleRepository.save(iamMsRole.get());
                }else throw new Exception("data not found");
    }
}
