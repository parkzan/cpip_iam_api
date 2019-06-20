package co.prior.iam.module.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemEditReq;
import co.prior.iam.repository.SystemRepository;

@Service
public class SystemEditService {


    SystemRepository systemRepository;

    public SystemEditService(SystemRepository systemRepository){

        this.systemRepository =systemRepository;

    }


    @Transactional
    public void editSystem(SystemEditReq systemEditReq) throws Exception{



        IamMsSystem iamMsSystem = systemRepository.findBySystemCodeAndIsDeleted(systemEditReq.getSystemCode(), "N")
                .orElseThrow(() -> new Exception("data not found"));


            iamMsSystem.setSystemName(systemEditReq.getNewName());
            iamMsSystem.setSystemIcon(systemEditReq.getNewIcon());

            systemRepository.save(iamMsSystem);


    }

}
