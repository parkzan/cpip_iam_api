package co.prior.iam.module.object.service;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.request.ObjectDeleteReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.repository.ObjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Service
public class ObjectDeleteService {

    ObjectRepository objectRepository;




    public ObjectDeleteService(ObjectRepository objectRepository ){

        this.objectRepository = objectRepository;

    }

    @Transactional
    public void deleteObject(ObjectDeleteReq objectDeleteReq) throws Exception{


        Optional<IamMsObject> root = objectRepository.findBySystemIdAndObjectCodeAndIsDeleted(objectDeleteReq.getSystemId(), objectDeleteReq.getObjectCode(), "N");
        List<IamMsObject> listObject = new ArrayList<>();
        Stack<IamMsObject> stack  = new Stack<>() ;
        if (root.isPresent()){

            if(root.get() != null){

                listObject.add(root.get());
                stack.push(root.get());

                addChild(root.get() , listObject , stack);
            }


            for (int i = 0 ; i<listObject.size() ; i++){
                listObject.get(i).setIsDeleted("Y");
                objectRepository.save(listObject.get(i));
            }




        }else throw new Exception("data not found");

    }

    private  void addChild(IamMsObject root , List<IamMsObject> listObject , Stack<IamMsObject> stack) {

            Optional<List<IamMsObject>> listChild = objectRepository.findByIsDeleted("N");
             stack.pop();
            if(listChild.isPresent()){
                for (IamMsObject list: listChild.get()) {

                        if (list != null){
                            if(list.getObjectParentId() == root.getObjectId()){
                                stack.push(list);
                            }
                        }


                }
            }

            if(!stack.empty()){
                listObject.add(stack.peek());
                addChild(stack.peek() , listObject , stack);

        }
            
        }
    }

