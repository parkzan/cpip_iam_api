package co.prior.iam.module.system.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemAddReq;
import co.prior.iam.repository.SystemRepository;

@Service
public class SystemCreateService {


    SystemRepository systemRepository;

    public  SystemCreateService(SystemRepository systemRepository){

        this.systemRepository = systemRepository ;
    }

    @Transactional
    public void createSystem(SystemAddReq systemAddReq) throws Exception {


       Optional<IamMsSystem> check = systemRepository.findBySystemCodeAndIsDeleted(systemAddReq.getSystemCode(),"N");

            if (!check.isPresent()) {

                 IamMsSystem iamMsSystem = new IamMsSystem();
                 iamMsSystem.setSystemCode(systemAddReq.getSystemCode());
                 iamMsSystem.setSystemIcon(systemAddReq.getSystemIcon());
                 iamMsSystem.setSystemName(systemAddReq.getSystemName());
                 systemRepository.save(iamMsSystem);



             }
            else {

                 throw new Exception("System code duplicate");

            }

    }

}
