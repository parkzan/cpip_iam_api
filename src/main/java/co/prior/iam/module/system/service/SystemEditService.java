package co.prior.iam.module.system.service;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemEditReq;
import co.prior.iam.module.system.model.respone.SystemRespone;
import co.prior.iam.reposity.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class SystemEditService {

    @Autowired
    SystemRepository systemRepository;

    @Transactional
    public void editSystem(SystemEditReq systemEditReq) throws Exception{



        Optional<IamMsSystem> iamMsSystem = systemRepository.findBySystemCodeAndIsDeleted(systemEditReq.getSystemCode(), "N");

        if (iamMsSystem.isPresent()) {

            iamMsSystem.get().setSystemName(systemEditReq.getNewName());
            iamMsSystem.get().setSystemIcon(systemEditReq.getNewIcon());

            systemRepository.save(iamMsSystem.get());


        }
        else{

            throw new Exception("data not found");
        }


    }

}
