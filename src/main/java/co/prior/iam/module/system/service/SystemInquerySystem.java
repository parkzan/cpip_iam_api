package co.prior.iam.module.system.service;

import java.util.ArrayList;
import java.util.List;

import co.prior.iam.module.system.model.respone.SystemRespone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.repository.SystemRepository;

@Service
public class SystemInquerySystem {

    @Autowired
    SystemRepository systemRepository;

    public List<SystemRespone> inquerySystem() throws Exception{

        List<IamMsSystem> iamMsSystemList = systemRepository.findByIsDeleted("N");
        SystemRespone respones = new SystemRespone();
        List<SystemRespone> list = new ArrayList<>();

        if(!iamMsSystemList.isEmpty()){
            for (IamMsSystem system : iamMsSystemList){
                respones.setSystemCode(system.getSystemCode());
                respones.setSystemId(system.getSystemId());
                respones.setSystemIcon(system.getSystemIcon());
                respones.setSystemName(system.getSystemName());

                list.add(respones);
            }
            return list ;
        }
        throw new Exception("data not found");
    }
}
