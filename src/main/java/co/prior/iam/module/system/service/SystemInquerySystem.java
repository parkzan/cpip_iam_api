package co.prior.iam.module.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.repository.SystemRepository;

@Service
public class SystemInquerySystem {

    @Autowired
    SystemRepository systemRepository;

    public List<IamMsSystem> inquerySystem() throws Exception{

        List<IamMsSystem> iamMsSystemList = systemRepository.findByIsDeleted("N");

        if(!iamMsSystemList.isEmpty()){

            return iamMsSystemList ;
        }
        throw new Exception("data not found");
    }
}
