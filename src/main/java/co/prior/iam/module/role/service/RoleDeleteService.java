package co.prior.iam.module.role.service;


import co.prior.iam.entity.RoleEntity;
import co.prior.iam.module.role.model.req.RoleDeleteReq;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleDeleteService {

    @Autowired
    RoleRepository roleRepository;

    public String deleteRole(RoleDeleteReq roleDeleteReq){

        if (!StringUtils.isBlank(roleDeleteReq.getRoleCode()) && !StringUtils.isBlank(roleDeleteReq.getSystemId())){
            RoleEntity roleEntity = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleDeleteReq.getRoleCode(),roleDeleteReq.getSystemId(),"N");
            if(roleEntity !=null){

                roleEntity.setIsDeleted("Y");

                roleRepository.save(roleEntity);

                return  "success" ;
            }


        }


        return  "fail";
    }



}
