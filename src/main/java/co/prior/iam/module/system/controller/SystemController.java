package co.prior.iam.module.system.controller;

import java.util.List;

import co.prior.iam.module.system.model.respone.SystemRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import co.prior.iam.module.system.service.SystemInqueryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@PreAuthorize("hasRole('ROLE_IAM_ADMIN')")
public class SystemController {

    private final SystemCreateService systemCreateService;
    private final SystemDeleteService systemDeleteService;
    private final SystemEditService systemEditService;
    private final SystemInqueryService systemInqueryService;

    public SystemController(SystemCreateService systemCreateService, SystemDeleteService systemDeleteService, 
    		SystemEditService systemEditService, SystemInqueryService systemInqueryService) {
    	
        this.systemCreateService = systemCreateService;
        this.systemDeleteService = systemDeleteService;
        this.systemEditService = systemEditService;
        this.systemInqueryService = systemInqueryService;
    }

    @PostMapping("/system")
    public ResponseEntity<IamMsSystem> createSystem(@RequestBody SystemAddReq systemAddReq) {
        log.info("Controller createSystem: {}", systemAddReq);
        


        return ResponseEntity.created(null).body(this.systemCreateService.createSystem(systemAddReq));
    }

    @DeleteMapping("/system")
    public ResponseEntity<Void> deleteSystem(@RequestBody SystemDeleteReq systemDeleteReq) {
        log.info("Controller deleteSystem: {}", systemDeleteReq);
        
        this.systemDeleteService.deleteSystem(systemDeleteReq);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/system")
    public ResponseEntity<Void> editSystem(@RequestBody SystemEditReq systemEditReq) {
        log.info("Controller editSystem: {}", systemEditReq);
        
        this.systemEditService.editSystem(systemEditReq);

        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/systems")
    public ResponseEntity<List<SystemRespone>> inquerySystem() {
        log.info("Controller inquerySystem");
        
        List<SystemRespone> list = this.systemInqueryService.inquerySystem();

        return ResponseEntity.ok(list);
    }

}
