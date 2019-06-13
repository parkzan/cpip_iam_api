package co.prior.iam.module.object.controller;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.module.object.model.request.ObjectDeleteReq;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.module.object.service.ObjectCreateService;
import co.prior.iam.module.object.service.ObjectDeleteService;
import co.prior.iam.module.object.service.ObjectEditService;
import co.prior.iam.module.object.service.ObjectInqueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/object")
public class ObjectController {


    private final ObjectCreateService objectCreateService;


    private final ObjectEditService objectEditService;


    private final ObjectInqueryService objectInqueryService;


    private final ObjectDeleteService objectDeleteService;

    public ObjectController(ObjectCreateService objectCreateService, ObjectEditService objectEditService, ObjectInqueryService objectInqueryService, ObjectDeleteService objectDeleteService) {
        this.objectCreateService = objectCreateService;
        this.objectEditService = objectEditService;
        this.objectInqueryService = objectInqueryService;
        this.objectDeleteService = objectDeleteService;
    }


    @PostMapping
    public ResponseEntity<ObjectRespone> createObject(@RequestBody ObjectCreateReq objectCreateReq) throws Exception {

        ObjectRespone respone = new ObjectRespone();
        objectCreateService.createObject(objectCreateReq);
        respone.setCode("S");
        respone.setMessage("success");

        return new ResponseEntity<>(respone,HttpStatus.CREATED);


    }

    @PutMapping
    public ResponseEntity<ObjectRespone> editObject(@RequestBody ObjectEditReq objectEditReq) throws Exception {

        ObjectRespone respone = new ObjectRespone();
        objectEditService.editObject(objectEditReq);
        respone.setCode("S");
        respone.setMessage("success");

        return new ResponseEntity<>(respone,HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<ObjectRespone> deleteObject(@RequestBody ObjectDeleteReq objectDeleteReq) throws  Exception {

        ObjectRespone respone = new ObjectRespone();
        objectDeleteService.deleteObject(objectDeleteReq);
        respone.setCode("S");
        respone.setMessage("success");

        return new ResponseEntity<>(respone,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<IamMsObject>> inqueryObject(@RequestParam Long systemId) throws Exception {
        Optional<List<IamMsObject>> list = objectInqueryService.inqueryObject(systemId);

        if(list.isPresent()){
            return new ResponseEntity<>(list.get(),HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);

    }
}
