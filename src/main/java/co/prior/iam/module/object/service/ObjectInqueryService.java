package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.repository.ObjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectInqueryService {

   ObjectRepository objectRepository ;

   public  ObjectInqueryService(ObjectRepository objectRepository){

       this.objectRepository = objectRepository;

   }

   @Transactional
    public List<IamMsObject> inqueryObject(Long systemId) throws Exception {



       List<IamMsObject> listModel = objectRepository.findBySystemIdAndIsDeleted(systemId,"N");

       if(!listModel.isEmpty()){

           return listModel;

       }
       throw new Exception("data not found");

   }


}
