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


    List<IamMsObject>  listObject  ;

    Stack<IamMsObject> stack  ;

    public ObjectDeleteService(ObjectRepository objectRepository ){

        this.objectRepository = objectRepository;
        listObject = new ArrayList<>();
        stack  = new Stack<>() ;
    }

    @Transactional
    public ResponseEntity<ObjectRespone> deleteObject(ObjectDeleteReq objectDeleteReq) throws Exception{
        ObjectRespone respone = new ObjectRespone();

        Optional<IamMsObject> root = objectRepository.findBySystemIdAndObjectCodeAndIsDeleted(objectDeleteReq.getSystemId(), objectDeleteReq.getObjectCode(), "N");

        if (root.isPresent()){

            if(root.get() != null){

                listObject.add(root.get());
                stack.push(root.get());

                addChild(root.get());
            }


            for (int i = 0 ; i<listObject.size() ; i++){
                listObject.get(i).setIsDeleted("Y");
                objectRepository.save(listObject.get(i));
            }

            respone.setCode("S001");
            respone.setMessage("Success");

            return new ResponseEntity<>(respone, HttpStatus.OK);


        }

        respone.setCode("E001");
        respone.setMessage("data not found");
        return new ResponseEntity<>(respone,HttpStatus.NOT_FOUND);

    }

    private  void addChild(IamMsObject root) throws Exception{

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
                addChild(stack.peek());

        }
            
        }
    }

