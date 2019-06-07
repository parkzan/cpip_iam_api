package co.prior.iam.module.System.service;


import co.prior.iam.entity.SystemEntity;
import co.prior.iam.module.System.model.SystemModel;
import co.prior.iam.module.System.model.req.SystemAddReq;
import co.prior.iam.reposity.SystemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class SystemCreateService {
    @Autowired
    SystemRepository systemRepository;

    @Transactional
    public  String createSystem(SystemAddReq systemAddReq){


   if(!StringUtils.isBlank(systemAddReq.getSystemCode()) && !StringUtils.isBlank(systemAddReq.getSystemName()) ){
       SystemEntity check = systemRepository.findBySystemCodeAndIsDeleted(systemAddReq.getSystemCode(),"N");

if (check == null) {
    SystemEntity systemEntity = new SystemEntity();
    systemEntity.setSystemCode(systemAddReq.getSystemCode());
    systemEntity.setSystemIcon(systemAddReq.getSystemIcon());
    systemEntity.setSystemName(systemAddReq.getSystemName());
    systemEntity.setCreatedBy("ADMIN");
    systemEntity.setCreatedDate(new Date());
    systemEntity.setIsDeleted("N");

    systemRepository.save(systemEntity);

    return "success";
}
else{
    SystemEntity systemEntity = systemRepository.findBySystemCodeAndIsDeleted(systemAddReq.getSystemCode(),"Y");
    if (systemEntity != null ){
        //// upadte

        systemEntity.setSystemIcon(systemAddReq.getSystemIcon());
        systemEntity.setSystemName(systemAddReq.getSystemName());
        systemEntity.setCreatedBy("API");
        systemEntity.setCreatedDate(new Date());
        systemEntity.setIsDeleted("N");

        systemRepository.save(systemEntity);

        return "success";
    }

}
   }

return "ok";

    }

}
