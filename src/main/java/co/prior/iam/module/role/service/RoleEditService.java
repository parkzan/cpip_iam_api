package co.prior.iam.module.role.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.role.model.request.RoleEditReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RoleEditService {


    RoleRepository roleRepository;
    SystemRepository systemRepository;

    public RoleEditService(RoleRepository roleRepository , SystemRepository systemRepository){

        this.roleRepository = roleRepository;
        this.systemRepository=systemRepository;

    }


    @Transactional
    public void editRole(RoleEditReq roleEditReq) throws Exception{

        log.info("Service editRole: {}", roleEditReq);




                IamMsRole iamMsRole = roleRepository.findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(roleEditReq.getRoleCode(),roleEditReq.getSystemId(),"N")
                        .orElseThrow(() -> new Exception("data not found"));


                    iamMsRole.setRoleName(roleEditReq.getNewName());
                    iamMsRole.setRoleIcon(roleEditReq.getNewIcon());
                    roleRepository.save(iamMsRole);

    }
}
