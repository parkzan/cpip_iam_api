package co.prior.iam.module.role.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.req.RoleDeleteReq;
import co.prior.iam.module.role.model.res.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<RoleRespone> deleteRole(RoleDeleteReq roleDeleteReq) throws Exception{
          RoleRespone respone = new RoleRespone();

            Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleDeleteReq.getRoleCode(),roleDeleteReq.getSystemId(),"N");
            if(iamMsRole.isPresent()){

                iamMsRole.get().setIsDeleted("Y");

                roleRepository.save(iamMsRole.get());

                respone.setCode("S001");
                respone.setMessage("success" );

                return  new ResponseEntity<>(respone,HttpStatus.OK);
            }

        respone.setCode("E001");
        respone.setMessage("data not found" );

        return  new ResponseEntity<>(respone,HttpStatus.NOT_FOUND);
    }



}
