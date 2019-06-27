package co.prior.iam.module.object.service;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.error.DataDuplicateException;
import co.prior.iam.error.DataNotFoundException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
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
        log.info("Service createObject: {}", objectCreateReq);
        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(objectCreateReq.getSystemId(),"N")
                .orElseThrow(() -> new DataNotFoundException("data not found"));

        Optional<IamMsObject> iamMsObject = objectRepository.findByIamMsSystemAndObjectCodeAndIsDeleted(iamMsSystem,objectCreateReq.getObjectCode(),"N");
        IamMsObject parentObject = objectRepository.findByIamMsSystem_SystemIdAndObjectIdAndIsDeleted(objectCreateReq.getSystemId(),objectCreateReq.getObjectParentId(),"N")
                .orElseThrow(() -> new DataNotFoundException("data not found"));
        if(!iamMsObject.isPresent()){


            IamMsObject model = new IamMsObject();
            model.setObjectCode(objectCreateReq.getObjectCode());
            model.setObjectName(objectCreateReq.getObjectName());
            model.setIamMsSystem(iamMsSystem);
            model.setObjectParent(parentObject);


            objectRepository.save(model);



        }else throw new DataDuplicateException("99","Object code duplicate");

    }

}
