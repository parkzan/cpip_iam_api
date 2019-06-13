package co.prior.iam.module.role.service;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleDeleteService {


    RoleRepository roleRepository;

    public  RoleDeleteService(RoleRepository roleRepository){

        this.roleRepository = roleRepository;
    }


    @Transactional
    public void deleteRole(RoleDeleteReq roleDeleteReq) throws Exception{


            Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleDeleteReq.getRoleCode(),roleDeleteReq.getSystemId(),"N");
            if(iamMsRole.isPresent()){

                iamMsRole.get().setIsDeleted("Y");

                roleRepository.save(iamMsRole.get());


            }else throw new Exception("data not found");
    }



}
