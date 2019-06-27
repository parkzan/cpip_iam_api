package co.prior.iam.module.object.controller;


import co.prior.iam.entity.IamMsObject;
import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.module.object.model.request.ObjectDeleteReq;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.module.object.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/object")
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
        log.info("Controller createObject: {}", objectCreateReq);
        objectCreateService.createObject(objectCreateReq);


        return ResponseEntity.created(null).build();


    }

    @PutMapping
    public ResponseEntity<Void> editObject(@RequestBody ObjectEditReq objectEditReq) throws Exception {

        log.info("Controller editObject: {}", objectEditReq);
        objectEditService.editObject(objectEditReq);


        return ResponseEntity.noContent().build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteObject(@RequestBody ObjectDeleteReq objectDeleteReq) throws  Exception {

        log.info("Controller deleteObject: {}", objectDeleteReq);
        objectDeleteService.deleteObject(objectDeleteReq);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/{system}")
    public ResponseEntity<List<IamMsObject>> inqueryObject(@PathVariable Long systemId) throws Exception {
        log.info("Controller inqueryObject: {}", systemId);
        List<IamMsObject> list = objectInqueryService.inqueryObject(systemId);


            return ResponseEntity.ok(list);




    }
    @GetMapping("/{systemId}/{objectId}")
    public ResponseEntity<List<IamMsObject>> inqueryChildObject(@PathVariable Long systemId ,@PathVariable  Long objectId) throws Exception {
        log.info("Controller inqueryChildObject: {}", objectId );
        List<IamMsObject> list = objectChildInqueryService.inqueryChildObject(systemId ,objectId);


        return ResponseEntity.ok(list);




    }
}
