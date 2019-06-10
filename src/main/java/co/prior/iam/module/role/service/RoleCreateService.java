package co.prior.iam.module.role.service;


import co.prior.iam.entity.RoleEntity;
import co.prior.iam.module.role.model.req.RoleCreateReq;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class RoleCreateService {

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public String createRole(RoleCreateReq roleCreateReq){
        if(!StringUtils.isBlank(roleCreateReq.getRoleCode()) && !StringUtils.isBlank(roleCreateReq.getRoleName()) && !StringUtils.isBlank(roleCreateReq.getSystemId())) {

            RoleEntity roleEntity = roleRepository.findByRoleCodeAndIsDeleted(roleCreateReq.getRoleCode(),"N");

            if (roleEntity == null){
                roleEntity.setRoleCode(roleCreateReq.getRoleCode());
                roleEntity.setIsDeleted("N");
                roleEntity.setRoleName(roleCreateReq.getRoleName());
                roleEntity.setRoleIcon(roleCreateReq.getRoleIcon());
                roleEntity.setSystemId(Long.parseLong(roleCreateReq.getSystemId()) );
                roleEntity.setCreatedBy("ADMIN");
                roleEntity.setCreatedDate(new Date());


                roleRepository.save(roleEntity);

                return "success";

            }


        }
        return  "fail" ;
    }
}
