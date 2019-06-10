package co.prior.iam.module.role.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.req.RoleDeleteReq;
import co.prior.iam.module.role.model.res.RoleRespone;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleDeleteService {

    @Autowired
    RoleRepository roleRepository;


    @Transactional
    public BaseApiRespone<RoleRespone> deleteRole(RoleDeleteReq roleDeleteReq) throws Exception{
          BaseApiRespone<RoleRespone> respone = new BaseApiRespone<>();
        if (!StringUtils.isBlank(roleDeleteReq.getRoleCode()) && !StringUtils.isBlank(roleDeleteReq.getSystemId())){
            Optional<IamMsRole> iamMsRole = roleRepository.findByRoleCodeAndSystemIdAndIsDeleted(roleDeleteReq.getRoleCode(),Long.parseLong(roleDeleteReq.getSystemId()),"N");
            if(iamMsRole.isPresent()){

                iamMsRole.get().setIsDeleted("Y");

                roleRepository.save(iamMsRole.get());

                respone.setResCode(HttpStatus.OK.toString());
                respone.setMessage("delete  " + iamMsRole.get().getRoleCode() +" success" );

                return  respone;
            }
            else {
                throw new Exception("not found data");
            }


        }


        return  respone;
    }



}
