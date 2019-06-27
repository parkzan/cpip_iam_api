package co.prior.iam.module.system.service;

import java.util.ArrayList;
import java.util.List;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.module.system.model.respone.SystemRespone;
import co.prior.iam.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.repository.SystemRepository;

@Slf4j
@Service
public class SystemInqueryService {


    SystemRepository systemRepository;


    public  SystemInqueryService(SystemRepository systemRepository){
        this.systemRepository =systemRepository;


    }

    public List<IamMsSystem> inquerySystem() throws Exception{
        log.info("Service inquerySystem: {}");
        List<IamMsSystem> iamMsSystemList = systemRepository.findByIsDeleted("N");

        if(!iamMsSystemList.isEmpty()){

            return iamMsSystemList ;
        }
        throw new DataNotFoundException("data not found");
    }
}
