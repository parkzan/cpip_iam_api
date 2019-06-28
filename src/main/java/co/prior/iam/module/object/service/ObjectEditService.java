package co.prior.iam.module.object.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.repository.ObjectRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ObjectEditService {


    ObjectRepository objectRepository;


    public ObjectEditService(ObjectRepository objectRepository ) {

        this.objectRepository = objectRepository;

    }


    @Transactional
    public void editObject(ObjectEditReq objectEditReq) {

        log.info("Service editObject: {}", objectEditReq);

        IamMsObject iamMsObject = objectRepository.findByObjectId(objectEditReq.getObjectId())
                .orElseThrow(() -> new DataNotFoundException("data not found"));


        iamMsObject.setObjectName(objectEditReq.getNewName());

        objectRepository.save(iamMsObject);


    }

}
