package co.prior.iam.module.System.service;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.System.model.req.SystemDeleteReq;
import co.prior.iam.module.System.model.res.SystemRespone;
import co.prior.iam.reposity.SystemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class SystemDeleteService {


    SystemRepository systemRepository;

    public SystemDeleteService(SystemRepository systemRepository){
        this.systemRepository = systemRepository ;
    }

    @Transactional
    public ResponseEntity<SystemRespone> deleteSystem(SystemDeleteReq systemDeleteReq){
        SystemRespone respone = new SystemRespone();

            Optional<IamMsSystem> iamMsSystem = systemRepository.findBySystemCodeAndIsDeleted(systemDeleteReq.getSystemCode(),"N");

            if(iamMsSystem.isPresent()){
                iamMsSystem.get().setIsDeleted("Y");

                systemRepository.save(iamMsSystem.get());

                respone.setCode("S001");
                respone.setMessage("success" );

                return new ResponseEntity<>(respone,HttpStatus.OK);
            }


        respone.setCode("E001");
        respone.setMessage("data not found");

        return new ResponseEntity<>(respone,HttpStatus.NOT_FOUND);
    }
}
