package co.prior.iam.module.object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class ObjectCreateService {


    ObjectRepository objectRepository;

    public ObjectCreateService(ObjectRepository objectRepository){
        this.objectRepository = objectRepository;
    }

    @Transactional
    public ResponseEntity<ObjectRespone> createObject(ObjectCreateReq objectCreateReq) throws Exception{

        ObjectRespone respone = new ObjectRespone();

        Optional<IamMsObject> iamMsObject = objectRepository.findBySystemIdAndObjectCodeAndIsDeleted(objectCreateReq.getSystemId(),objectCreateReq.getObjectCode(),"N");

        if(!iamMsObject.isPresent()){


            IamMsObject model = new IamMsObject();
            model.setObjectCode(objectCreateReq.getObjectCode());
            model.setObjectName(objectCreateReq.getObjectName());
            model.setSystemId(objectCreateReq.getSystemId());
            model.setObjectParentId(objectCreateReq.getObjectParentId());
            model.setCreatedBy("ADMIN");
            model.setCreatedDate(new Date());
            model.setIsDeleted("N");

            respone.setCode("S001");
            respone.setMessage("Success");

            objectRepository.save(model);
            return new ResponseEntity<>(respone,HttpStatus.CREATED);


        }
        respone.setCode("E001");
        respone.setMessage("data not found");
        return new ResponseEntity<>(respone,HttpStatus.NOT_FOUND);
    }
}
