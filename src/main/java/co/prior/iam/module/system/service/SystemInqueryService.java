package co.prior.iam.module.system.service;

import java.util.ArrayList;
import java.util.List;

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.system.model.respone.SystemRespone;
import co.prior.iam.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.repository.SystemRepository;

@Service
public class SystemInqueryService {


    SystemRepository systemRepository;


    public  SystemInqueryService(SystemRepository systemRepository){
        this.systemRepository =systemRepository;


    }

    public List<IamMsSystem> inquerySystem() throws Exception{

        List<IamMsSystem> iamMsSystemList = systemRepository.findByIsDeleted("N");

        if(!iamMsSystemList.isEmpty()){

            return iamMsSystemList ;
        }
        throw new Exception("data not found");
    }
}
