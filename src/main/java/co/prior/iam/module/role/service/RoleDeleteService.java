package co.prior.iam.module.role.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class RoleDeleteService {


    RoleRepository roleRepository;
    SystemRepository systemRepository;

    public  RoleDeleteService(RoleRepository roleRepository , SystemRepository systemRepository){

        this.roleRepository = roleRepository;
        this.systemRepository = systemRepository;
    }


    @Transactional
    public void deleteRole(RoleDeleteReq roleDeleteReq) throws Exception{
        log.info("Service deleteRole: {}", roleDeleteReq);



            IamMsRole iamMsRole = roleRepository.findByRoleCodeAndIamMsSystem_SystemIdAndIsDeleted(roleDeleteReq.getRoleCode(),roleDeleteReq.getSystemId(),"N")
                    .orElseThrow(() -> new DataNotFoundException("data not found"));

                iamMsRole.setIsDeleted("Y");

                roleRepository.save(iamMsRole);


            }
    }




