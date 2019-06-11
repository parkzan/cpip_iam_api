package co.prior.iam.module.System.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.System.model.req.SystemDeleteReq;
import co.prior.iam.module.System.model.res.SystemRespone;
import co.prior.iam.reposity.SystemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class SystemDeleteService {

    @Autowired
    SystemRepository systemRepository;

    @Transactional
    public BaseApiRespone<SystemRespone> deleteSystem(SystemDeleteReq systemDeleteReq){
        BaseApiRespone<SystemRespone> respone = new BaseApiRespone<>();
        if(!StringUtils.isBlank(systemDeleteReq.getSystemCode())){

            Optional<IamMsSystem> iamMsSystem = systemRepository.findBySystemCodeAndIsDeleted(systemDeleteReq.getSystemCode(),"N");

            if(iamMsSystem != null){
                iamMsSystem.get().setIsDeleted("Y");

                systemRepository.save(iamMsSystem.get());

                respone.setResCode(HttpStatus.OK.toString());
                respone.setMessage("delete " + iamMsSystem.get().getSystemCode() +" success" );

                return respone;
            }
        }

        return respone;
    }
}
