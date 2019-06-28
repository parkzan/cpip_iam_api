package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class ObjectEditService {


    ObjectRepository objectRepository;


    public ObjectEditService(ObjectRepository objectRepository ) {

        this.objectRepository = objectRepository;

    }


    @Transactional
    public void editObject(ObjectEditReq objectEditReq) throws Exception {

        log.info("Service editObject: {}", objectEditReq);

        IamMsObject iamMsObject = objectRepository.findByObjectId(objectEditReq.getObjectId())
                .orElseThrow(() -> new DataNotFoundException("data not found"));


        iamMsObject.setObjectName(objectEditReq.getNewName());

        objectRepository.save(iamMsObject);


    }

}
