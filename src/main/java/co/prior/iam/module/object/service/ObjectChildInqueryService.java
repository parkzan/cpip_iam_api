package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ObjectChildInqueryService {

    private SystemRepository systemRepository;
    private ObjectRepository objectRepository;

    public ObjectChildInqueryService(SystemRepository systemRepository , ObjectRepository objectRepository){

        this.systemRepository = systemRepository;
        this.objectRepository=objectRepository;

    }


    @Transactional
    public  List<IamMsObject> inqueryChildObject(Long systemId , Long objectId) throws Exception{
        log.info("Service inqueryChildObject: {}", objectId);

        List<IamMsObject> listObject = objectRepository.findByIamMsSystem_SystemIdAndObjectParent_ObjectIdAndIsDeleted(systemId,objectId,"N");




        if (listObject.isEmpty()){

            throw new DataNotFoundException("data not found");
        }


       return  listObject;

    }
}
