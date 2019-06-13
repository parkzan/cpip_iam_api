package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ObjectEditService {


    ObjectRepository objectRepository;

    public ObjectEditService(ObjectRepository objectRepository){

        this.objectRepository = objectRepository;
    }


    @Transactional
    public ResponseEntity<ObjectRespone> editObject(ObjectEditReq objectEditReq) throws Exception {


        Optional<IamMsObject> iamMsObject = objectRepository.findByObjectId(objectEditReq.getObjectId());


        if(iamMsObject.isPresent()){

            iamMsObject.get().setObjectName(objectEditReq.getNewName());

            objectRepository.save(iamMsObject.get());

        }throw new Exception("data not found");


    }
}
