package co.prior.iam.module.System.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.System.model.req.SystemAddReq;
import co.prior.iam.module.System.model.res.SystemRespone;
import co.prior.iam.reposity.SystemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;

@Service
public class SystemCreateService {
    @Autowired
    SystemRepository systemRepository;

    @Transactional
    public BaseApiRespone<SystemRespone> createSystem(SystemAddReq systemAddReq) throws Exception {
        BaseApiRespone<SystemRespone> respone = new BaseApiRespone<>();
   if(!StringUtils.isBlank(systemAddReq.getSystemCode()) && !StringUtils.isBlank(systemAddReq.getSystemName()) ){
       Optional<IamMsSystem> check = systemRepository.findBySystemCodeAndIsDeleted(systemAddReq.getSystemCode(),"N");

            if (!check.isPresent()) {
                 IamMsSystem iamMsSystem = new IamMsSystem();
                 iamMsSystem.setSystemCode(systemAddReq.getSystemCode());
                 iamMsSystem.setSystemIcon(systemAddReq.getSystemIcon());
                 iamMsSystem.setSystemName(systemAddReq.getSystemName());
                 iamMsSystem.setCreatedBy("ADMIN");
                 iamMsSystem.setCreatedDate(new Date());
                 iamMsSystem.setIsDeleted("N");
                 iamMsSystem.setUpdatedBy(null);
                 iamMsSystem.setUpdatedDate(null);

                 systemRepository.save(iamMsSystem);


                 respone.setResCode(HttpStatus.OK.toString());
                respone.setMessage("create " + iamMsSystem.getSystemCode() +" success" );


                return respone;
             }
            else {
                throw new Exception("duplicate System Code");
            }

   }

return respone;

    }

}
