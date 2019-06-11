package co.prior.iam.module.object.controller;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.req.GetObjectReq;
import co.prior.iam.module.object.model.req.ObjectCreateReq;
import co.prior.iam.module.object.model.req.ObjectEditReq;
import co.prior.iam.module.object.model.res.ObjectRespone;
import co.prior.iam.module.object.service.ObjectCreateService;
import co.prior.iam.module.object.service.ObjectEditService;
import co.prior.iam.module.object.service.ObjectInqueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class ObjectController {

    @Autowired
    ObjectCreateService objectCreateService;

    @Autowired
    ObjectEditService objectEditService;

    @Autowired
    ObjectInqueryService objectInqueryService;



    @PostMapping(path = "/object/createObject")
    public ResponseEntity<ObjectRespone> createObject(@RequestBody ObjectCreateReq objectCreateReq) throws Exception {


        return objectCreateService.createObject(objectCreateReq);

    }

    @PutMapping(path = "/object/editObject")
    public ResponseEntity<ObjectRespone> editObject(@RequestBody ObjectEditReq objectEditReq) throws Exception {

        return objectEditService.editObject(objectEditReq);
    }

    @PostMapping(path = "/object/inqueryObject")
    public ResponseEntity<List<IamMsObject>> inqueryObject(@RequestBody GetObjectReq getObjectReq) throws Exception {
        Optional<List<IamMsObject>> list = objectInqueryService.inqueryObject(getObjectReq);

        if(list.isPresent()){
            return new ResponseEntity<>(list.get(),HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }
}
