package co.prior.iam.module.System.service;


import co.prior.iam.entity.SystemEntity;
import co.prior.iam.reposity.SystemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemInquerySystem {

    @Autowired
    SystemRepository systemRepository;

    public List<SystemEntity> inquerySystem(){

        List<SystemEntity> systemEntityList = systemRepository.findByIsDeleted("N");

        return  systemEntityList;
    }
}
