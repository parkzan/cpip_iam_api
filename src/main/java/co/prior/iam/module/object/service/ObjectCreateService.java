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
    public void createObject(ObjectCreateReq objectCreateReq) throws Exception{



        Optional<IamMsObject> iamMsObject = objectRepository.findBySystemIdAndObjectCodeAndIsDeleted(objectCreateReq.getSystemId(),objectCreateReq.getObjectCode(),"N");

        if(!iamMsObject.isPresent()){


            IamMsObject model = new IamMsObject();
            model.setObjectCode(objectCreateReq.getObjectCode());
            model.setObjectName(objectCreateReq.getObjectName());
            model.setSystemId(objectCreateReq.getSystemId());
            model.setObjectParentId(objectCreateReq.getObjectParentId());


            objectRepository.save(model);



        }else throw new Exception("data not found");

    }
}
