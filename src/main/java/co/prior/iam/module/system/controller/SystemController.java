package co.prior.iam.module.system.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemAddReq;
import co.prior.iam.module.system.model.request.SystemDeleteReq;
import co.prior.iam.module.system.model.request.SystemEditReq;
import co.prior.iam.module.system.service.SystemCreateService;
import co.prior.iam.module.system.service.SystemDeleteService;
import co.prior.iam.module.system.service.SystemEditService;
import co.prior.iam.module.system.service.SystemInquerySystem;

@RestController
@RequestMapping(path = "/system")
public class SystemController {


    private final SystemCreateService systemCreateService;


    private final SystemDeleteService systemDeleteService;


    private final SystemEditService systemEditService;


    private final SystemInquerySystem systemInquerySystem;

    public SystemController(SystemCreateService systemCreateService, SystemDeleteService systemDeleteService, SystemEditService systemEditService, SystemInquerySystem systemInquerySystem) {
        this.systemCreateService = systemCreateService;
        this.systemDeleteService = systemDeleteService;
        this.systemEditService = systemEditService;
        this.systemInquerySystem = systemInquerySystem;
    }

    @PostMapping
    public ResponseEntity<Void> createSystem(@RequestBody SystemAddReq systemAddReq) throws Exception {

        systemCreateService.createSystem(systemAddReq);

        return ResponseEntity.created(null).build();

    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSystem(@RequestBody SystemDeleteReq systemDeleteReq) throws Exception  {

        systemDeleteService.deleteSystem(systemDeleteReq);

        return ResponseEntity.noContent().build();

    }

    @PutMapping
    public ResponseEntity<Void> editSystem(@RequestBody SystemEditReq systemEditReq) throws Exception {

        systemEditService.editSystem(systemEditReq);

        return  ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<IamMsSystem>> inquerySystem() throws Exception  {
        List<IamMsSystem> list = systemInquerySystem.inquerySystem();


            return ResponseEntity.ok(list);



    }


}
