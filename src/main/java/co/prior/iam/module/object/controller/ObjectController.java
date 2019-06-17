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


        objectCreateService.createObject(objectCreateReq);


        return new ResponseEntity<>(HttpStatus.CREATED);


    }

    @PutMapping
    public ResponseEntity<ObjectRespone> editObject(@RequestBody ObjectEditReq objectEditReq) throws Exception {


        objectEditService.editObject(objectEditReq);


        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<ObjectRespone> deleteObject(@RequestBody ObjectDeleteReq objectDeleteReq) throws  Exception {


        objectDeleteService.deleteObject(objectDeleteReq);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    @RequestMapping(path = "/{systemId}")
    public ResponseEntity<List<IamMsObject>> inqueryObject(@PathVariable Long systemId) throws Exception {
        List<IamMsObject> list = objectInqueryService.inqueryObject(systemId);


            return new ResponseEntity<>(list,HttpStatus.OK);




    }
}
