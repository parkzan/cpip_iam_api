package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.repository.ObjectRepository;
import co.prior.iam.repository.SystemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ObjectInqueryService {

   ObjectRepository objectRepository ;
   SystemRepository systemRepository;


   public  ObjectInqueryService(ObjectRepository objectRepository , SystemRepository systemRepository){

       this.objectRepository = objectRepository;
       this.systemRepository = systemRepository;

   }

   @Transactional
    public List<IamMsObject> inqueryObject(Long systemId) throws Exception {


       IamMsSystem iamMsSystem = systemRepository.findBySystemIdAndIsDeleted(systemId,"N")
               .orElseThrow(() -> new Exception("data not found"));


       List<IamMsObject> listModel = objectRepository.findByIamMsSystemAndIsDeleted(iamMsSystem,"N");
       List<IamMsObject> list = new ArrayList<>() ;


       if(!listModel.isEmpty()){

           for (IamMsObject object : listModel){

               if(object.getObjectParentId() == null){
                   list.add(object);
               }
           }
           return list;

       }
       throw new Exception("data not found");

   }




}
