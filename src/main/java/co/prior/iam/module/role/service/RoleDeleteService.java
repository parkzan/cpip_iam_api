package co.prior.iam.module.role.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import co.prior.iam.repository.SystemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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


        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(roleDeleteReq.getSystemId(),"N")
                .orElseThrow(() -> new Exception("data not found"));

            IamMsRole iamMsRole = roleRepository.findByRoleCodeAndIamMsSystemAndIsDeleted(roleDeleteReq.getRoleCode(),iamMsSystem,"N")
                    .orElseThrow(() -> new Exception("data not found"));

                iamMsRole.setIsDeleted("Y");

                roleRepository.save(iamMsRole);


            }
    }




