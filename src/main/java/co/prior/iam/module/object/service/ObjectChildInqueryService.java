package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

        IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(systemId,"N")
                .orElseThrow(() -> new Exception("data not found"));

        List<IamMsObject> listObject = objectRepository.findByIamMsSystemAndIsDeleted(iamMsSystem,"N");

        List<IamMsObject> list = new ArrayList<>();


        if (!listObject.isEmpty()){
            for (IamMsObject obj : listObject){
                if (obj.getObjectParentId() == objectId){
                    list.add(obj);
                }
            }

            if(!list.isEmpty())
            return list;
            else throw new Exception(objectId + " does't have child");
        }
        else throw new Exception("data not found");



    }
}
