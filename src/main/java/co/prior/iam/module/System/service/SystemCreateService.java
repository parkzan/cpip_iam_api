package co.prior.iam.module.System.service;



import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.System.model.req.SystemAddReq;
import co.prior.iam.module.System.model.res.SystemRespone;
import co.prior.iam.reposity.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<SystemRespone> createSystem(SystemAddReq systemAddReq) throws Exception {
        SystemRespone respone = new SystemRespone();

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


                 respone.setCode("S001");
                respone.setMessage("success" );


                return new ResponseEntity<>(respone,HttpStatus.CREATED);
             }


        respone.setCode("E001");
        respone.setMessage("data not found");

        return new ResponseEntity<>(respone,HttpStatus.NOT_FOUND);
    }

}
