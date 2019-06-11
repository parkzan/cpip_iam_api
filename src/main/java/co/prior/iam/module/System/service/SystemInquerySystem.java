package co.prior.iam.module.System.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.reposity.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemInquerySystem {

    @Autowired
    SystemRepository systemRepository;

    public BaseApiRespone<List<IamMsSystem>> inquerySystem(){
        BaseApiRespone<List<IamMsSystem>> respone = new BaseApiRespone<>();
        Optional<List<IamMsSystem>> iamMsSystemList = systemRepository.findByIsDeleted("N");

        if(iamMsSystemList.isPresent()){

            respone.setResCode(HttpStatus.OK.toString());
            respone.setMessage("success" );
            respone.setResult(iamMsSystemList.get());
            return respone;
        }

        respone.setResCode(HttpStatus.NOT_FOUND.toString());
        respone.setMessage("data not found" );
        respone.setResult(iamMsSystemList.get());
        return respone;
    }
}
