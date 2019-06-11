package co.prior.iam.module.role.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.req.GetRoleReq;
import co.prior.iam.repository.RoleRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleInqueryService {

    @Autowired
    RoleRepository roleRepository;

    public BaseApiRespone<List<IamMsRole>> inqueryRole(GetRoleReq getRoleReq) throws  Exception{

        BaseApiRespone<List<IamMsRole>> respone = new BaseApiRespone<>();
        if (!StringUtils.isBlank(getRoleReq.getSystemId())){
             Optional<List<IamMsRole>> roleList = roleRepository.findBySystemIdAndIsDeleted(Long.parseLong(getRoleReq.getSystemId()),"N");
                if(roleList.isPresent()){

                    respone.setResCode(HttpStatus.OK.toString());
                    respone.setMessage("success" );
                    respone.setResult(roleList.get());
                    return respone;
                }else {
                    respone.setResCode(HttpStatus.NOT_FOUND.toString());
                    respone.setMessage("data not found");
                    respone.setResult(roleList.get());
                    return respone;

                }

        }
       throw new Exception("data not found");

    }
}
