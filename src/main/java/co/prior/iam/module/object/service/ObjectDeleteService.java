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


    public ObjectDeleteService(ObjectRepository objectRepository) {

        this.objectRepository = objectRepository;

    }

    @Transactional
    public void deleteObject(ObjectDeleteReq objectDeleteReq) throws Exception {


        IamMsObject root = objectRepository.findBySystemIdAndObjectCodeAndIsDeleted(objectDeleteReq.getSystemId(), objectDeleteReq.getObjectCode(), "N")
                .orElseThrow(() -> new Exception("data not found"));
        List<IamMsObject> listObject = new ArrayList<>();
        Stack<IamMsObject> stack = new Stack<>();


            listObject.add(root);
            stack.push(root);

            addChild(root, listObject, stack);



        for (int i = 0; i < listObject.size(); i++) {
            listObject.get(i).setIsDeleted("Y");
            objectRepository.save(listObject.get(i));
        }


    }

    private void addChild(IamMsObject root, List<IamMsObject> listObject, Stack<IamMsObject> stack) {

        List<IamMsObject> listChild = objectRepository.findBySystemIdAndIsDeleted(root.getSystemId().getSystemId(),"N");

        stack.pop();

        if(!listChild.isEmpty()) {

            for (IamMsObject list : listChild) {

                    if (list.getObjectParentId() == root.getObjectId()) {
                        stack.push(list);
                    }



            }


            if (!stack.empty()) {
                listObject.add(stack.peek());
                addChild(stack.peek(), listObject, stack);

            }
        }


    }
}

