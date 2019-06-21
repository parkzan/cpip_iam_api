package co.prior.iam.module.object.controller;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.module.object.model.request.ObjectDeleteReq;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.module.object.service.*;
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

    private final ObjectChildInqueryService objectChildInqueryService;

    public ObjectController(ObjectCreateService objectCreateService, ObjectEditService objectEditService, ObjectInqueryService objectInqueryService, ObjectDeleteService objectDeleteService, ObjectChildInqueryService objectChildInqueryService) {
        this.objectCreateService = objectCreateService;
        this.objectEditService = objectEditService;
        this.objectInqueryService = objectInqueryService;
        this.objectDeleteService = objectDeleteService;
        this.objectChildInqueryService = objectChildInqueryService;
    }


    @PostMapping
    public ResponseEntity<Void> createObject(@RequestBody ObjectCreateReq objectCreateReq) throws Exception {


        objectCreateService.createObject(objectCreateReq);


        return ResponseEntity.created(null).build();


    }

    @PutMapping
    public ResponseEntity<Void> editObject(@RequestBody ObjectEditReq objectEditReq) throws Exception {


        objectEditService.editObject(objectEditReq);


        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteObject(@RequestBody ObjectDeleteReq objectDeleteReq) throws  Exception {


        objectDeleteService.deleteObject(objectDeleteReq);

        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @RequestMapping(path = "/{systemId}")
    public ResponseEntity<List<IamMsObject>> inqueryObject(@PathVariable Long systemId) throws Exception {
        List<IamMsObject> list = objectInqueryService.inqueryObject(systemId);


            return ResponseEntity.ok(list);




    }
    @GetMapping
    @RequestMapping(path = "/{systemId}/{objectId}")
    public ResponseEntity<List<IamMsObject>> inqueryChildObject(@PathVariable Long systemId ,@PathVariable  Long objectId) throws Exception {
        List<IamMsObject> list = objectChildInqueryService.inqueryChildObject(systemId ,objectId);


        return ResponseEntity.ok(list);




    }
}
