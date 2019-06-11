package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.req.ObjectEditReq;
import co.prior.iam.module.object.model.res.ObjectRespone;
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
        ObjectRespone respone = new ObjectRespone();


        if(iamMsObject.isPresent()){

            iamMsObject.get().setObjectName(objectEditReq.getNewName());
            respone.setCode("S001");
            respone.setMessage("Success");

            objectRepository.save(iamMsObject.get());

            return new ResponseEntity<>(respone,HttpStatus.OK);
        }

            respone.setCode("E001");
            respone.setMessage("data not found");

            return new ResponseEntity<>(respone,HttpStatus.NOT_FOUND);


    }
}
