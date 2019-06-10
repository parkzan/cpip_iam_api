package co.prior.iam.module.role.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.req.RoleEditReq;
import co.prior.iam.module.role.model.res.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleEditService {

    @Autowired
    RoleRepository roleRepository;


    @Transactional
    public BaseApiRespone<RoleRespone> editRole(RoleEditReq roleEditReq) throws Exception{
            BaseApiRespone<RoleRespone> respone = new BaseApiRespone<>();
        if(!StringUtils.isBlank(roleEditReq.getSystemId()) && !StringUtils.isBlank(roleEditReq.getRoleCode())){


                Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleEditReq.getRoleCode(),Long.parseLong(roleEditReq.getSystemId()),"N");

                if(iamMsRole.isPresent()) {


                    iamMsRole.get().setRoleName(roleEditReq.getNewName());
                    iamMsRole.get().setRoleIcon(roleEditReq.getNewIcon());
                    roleRepository.save(iamMsRole.get());

                    respone.setResCode(HttpStatus.OK.toString());
                    respone.setMessage("edit " + iamMsRole.get().getRoleCode() +" success" );


                    return respone;
                }
                else{
                    throw new Exception("not found data");
                }


        }

        return respone;
    }
}
