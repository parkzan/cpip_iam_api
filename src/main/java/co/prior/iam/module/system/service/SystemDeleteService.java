package co.prior.iam.module.system.service;

import co.prior.iam.error.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemDeleteReq;
import co.prior.iam.repository.SystemRepository;

@Slf4j
@Service
public class SystemDeleteService {


    SystemRepository systemRepository;

    public SystemDeleteService(SystemRepository systemRepository){
        this.systemRepository = systemRepository ;
    }

    @Transactional
    public void deleteSystem(SystemDeleteReq systemDeleteReq) throws Exception{

            log.info("Service deleteSystem: {}", systemDeleteReq);
            IamMsSystem iamMsSystem = systemRepository.findBySystemCodeAndIsDeleted(systemDeleteReq.getSystemCode(),"N")
                    .orElseThrow(() -> new DataNotFoundException("data not found"));


                iamMsSystem.setIsDeleted("Y");

                systemRepository.save(iamMsSystem);






    }
}
