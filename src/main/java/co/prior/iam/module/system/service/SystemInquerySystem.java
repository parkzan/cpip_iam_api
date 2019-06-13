package co.prior.iam.module.system.service;


import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.reposity.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemInquerySystem {

    @Autowired
    SystemRepository systemRepository;

    public Optional<List<IamMsSystem>> inquerySystem(){

        Optional<List<IamMsSystem>> iamMsSystemList = systemRepository.findByIsDeleted("N");

        if(iamMsSystemList.isPresent()){

            return iamMsSystemList ;
        }


        return null;
    }
}
