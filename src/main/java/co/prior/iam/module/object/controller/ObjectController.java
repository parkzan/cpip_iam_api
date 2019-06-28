package co.prior.iam.module.object.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.module.object.model.request.ObjectCreateReq;
import co.prior.iam.module.object.model.request.ObjectDeleteReq;
import co.prior.iam.module.object.model.request.ObjectEditReq;
import co.prior.iam.module.object.model.respone.ObjectRespone;
import co.prior.iam.module.object.service.ObjectChildInqueryService;
import co.prior.iam.module.object.service.ObjectCreateService;
import co.prior.iam.module.object.service.ObjectDeleteService;
import co.prior.iam.module.object.service.ObjectEditService;
import co.prior.iam.module.object.service.ObjectInqueryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api")
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


    @PostMapping("/object")
    public ResponseEntity<Void> createObject(@RequestBody ObjectCreateReq objectCreateReq) {
        log.info("Controller createObject: {}", objectCreateReq);
        objectCreateService.createObject(objectCreateReq);


        return ResponseEntity.created(null).build();


    }

    @PutMapping("/object")
    public ResponseEntity<Void> editObject(@RequestBody ObjectEditReq objectEditReq) {

        log.info("Controller editObject: {}", objectEditReq);
        objectEditService.editObject(objectEditReq);


        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/object")
    public ResponseEntity<Void> deleteObject(@RequestBody ObjectDeleteReq objectDeleteReq) throws  Exception {

        log.info("Controller deleteObject: {}", objectDeleteReq);
        objectDeleteService.deleteObject(objectDeleteReq);

        return ResponseEntity.noContent().build();

    }


    @GetMapping("/objects/system/{systemId}")
    public ResponseEntity<List<ObjectRespone>> inqueryObject(@PathVariable Long systemId) {
        log.info("Controller inqueryObject: {}", systemId);
        List<ObjectRespone> list = objectInqueryService.inqueryObject(systemId);


            return ResponseEntity.ok(list);




    }

    @GetMapping("/objects/{objectId}/system/{systemId}")
    public ResponseEntity<List<ObjectRespone>> inqueryChildObject(@PathVariable Long systemId ,@PathVariable  Long objectId) {
        log.info("Controller inqueryChildObject: {}", objectId );
        List<ObjectRespone> list = objectChildInqueryService.inqueryChildObject(systemId ,objectId);


        return ResponseEntity.ok(list);




    }
}
