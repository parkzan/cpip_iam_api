package co.prior.iam.module.role.service;


import co.prior.iam.entity.RoleEntity;
import co.prior.iam.module.role.model.req.GetRoleReq;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleInqueryService {

    @Autowired
    RoleRepository roleRepository;

    public List<RoleEntity> inqueryRole(GetRoleReq getRoleReq){
if (!StringUtils.isBlank(getRoleReq.getSystemId())){
    List<RoleEntity> roleList = roleRepository.findBySystemIdAndIsDeleted(getRoleReq.getSystemId(),"N");

}

        return  null;

    }
}
