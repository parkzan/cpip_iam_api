package co.prior.iam.module.system.service;



import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemAddReq;
import co.prior.iam.module.system.model.respone.SystemRespone;
import co.prior.iam.reposity.SystemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.Optional;

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
