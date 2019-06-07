package co.prior.iam.module.System.service;

import co.prior.iam.entity.SystemEntity;
import co.prior.iam.module.System.model.req.SystemDeleteReq;
import co.prior.iam.module.System.model.req.SystemEditReq;
import co.prior.iam.reposity.SystemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SystemEditService {

    @Autowired
    SystemRepository systemRepository;

    @Transactional
    public  String editSystem(SystemEditReq systemEditReq){

        if(!StringUtils.isBlank(systemEditReq.getSystemCode())
                && !StringUtils.isBlank(systemEditReq.getNewName())
                && !StringUtils.isBlank(systemEditReq.getNewIcon())){

            SystemEntity systemEntity = systemRepository.findBySystemCodeAndIsDeleted(systemEditReq.getSystemCode(),"N");

            if (systemEntity !=null){

                systemEntity.setSystemName(systemEditReq.getNewName());
                systemEntity.setSystemIcon(systemEditReq.getNewIcon());

                systemRepository.save(systemEntity);

                return "success";
            }

        }

       return "fail";
    }


}
