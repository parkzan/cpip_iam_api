package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.request.GetObjectReq;
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
    public Optional<List<IamMsObject>> inqueryObject(GetObjectReq getObjectReq) throws Exception {



       Optional<List<IamMsObject>> listModel = objectRepository.findBySystemIdAndIsDeleted(getObjectReq.getSystemId(),"N");

       if(listModel.isPresent()){

           return listModel;

       }
       return null;

   }


}
