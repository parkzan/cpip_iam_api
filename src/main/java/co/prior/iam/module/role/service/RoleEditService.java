package co.prior.iam.module.role.service;


import co.prior.iam.entity.RoleEntity;
import co.prior.iam.module.role.model.req.RoleEditReq;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleEditService {

    @Autowired
    RoleRepository roleRepository;

    public String editRole(RoleEditReq roleEditReq){

        if(!StringUtils.isBlank(roleEditReq.getSystemId()) && !StringUtils.isBlank(roleEditReq.getRoleCode())){

            if (!StringUtils.isBlank(roleEditReq.getNewName())){
                RoleEntity roleEntity = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleEditReq.getRoleCode(),roleEditReq.getSystemId(),"N");

                roleEntity.setRoleName(roleEditReq.getNewName());

                roleRepository.save(roleEntity);


            }
            else if (!StringUtils.isBlank(roleEditReq.getNewIcon())){

                RoleEntity roleEntity = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleEditReq.getRoleCode(),roleEditReq.getSystemId(),"N");

                roleEntity.setRoleIcon(roleEditReq.getRoleCode());

                roleRepository.save(roleEntity);

            }
            else if(!StringUtils.isBlank(roleEditReq.getNewIcon()) && !StringUtils.isBlank(roleEditReq.getNewName())){


                RoleEntity roleEntity = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleEditReq.getRoleCode(),roleEditReq.getSystemId(),"N");
                roleEntity.setRoleName(roleEditReq.getNewName());
                roleEntity.setRoleIcon(roleEditReq.getRoleCode());

                roleRepository.save(roleEntity);

            }else {
                return "fail";
            }

            return "success";
        }

        return "fail";
    }
}
