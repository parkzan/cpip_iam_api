package co.prior.iam.module.object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class ObjectCreateService {


    ObjectRepository objectRepository;

    SystemRepository systemRepository;

    public ObjectCreateService(ObjectRepository objectRepository , SystemRepository systemRepository){
        this.objectRepository = objectRepository;
        this.systemRepository = systemRepository;
    }

    @Transactional
    public void createObject(ObjectCreateReq objectCreateReq) throws Exception{

        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(objectCreateReq.getSystemId(),"N")
                .orElseThrow(() -> new Exception("data not found"));

        Optional<IamMsObject> iamMsObject = objectRepository.findByIamMsSystemAndObjectCodeAndIsDeleted(iamMsSystem,objectCreateReq.getObjectCode(),"N");

        if(!iamMsObject.isPresent()){


            IamMsObject model = new IamMsObject();
            model.setObjectCode(objectCreateReq.getObjectCode());
            model.setObjectName(objectCreateReq.getObjectName());
            model.setIamMsSystem(iamMsSystem);
            model.setObjectParentId(objectCreateReq.getObjectParentId());


            objectRepository.save(model);



        }else throw new Exception("Object code duplicate");

    }
}
