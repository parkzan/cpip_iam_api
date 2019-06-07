package co.prior.iam.module.System.service;


import co.prior.iam.entity.SystemEntity;
import co.prior.iam.module.System.model.req.SystemAddReq;
import co.prior.iam.module.System.model.req.SystemDeleteReq;
import co.prior.iam.reposity.SystemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SystemDeleteService {

    @Autowired
    SystemRepository systemRepository;

    @Transactional
    public  String deleteSystem(SystemDeleteReq systemDeleteReq){

        if(!StringUtils.isBlank(systemDeleteReq.getSystemCode())){

            SystemEntity systemEntity = systemRepository.findBySystemCodeAndIsDeleted(systemDeleteReq.getSystemCode(),"N");

            if(systemEntity != null){
                systemEntity.setIsDeleted("Y");

                systemRepository.save(systemEntity);

                return "success";
            }
        }

        return "fail";
    }
}
