package co.prior.iam.module.role.controller;


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

import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.request.RoleCreateReq;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.module.role.model.request.RoleEditReq;
import co.prior.iam.module.role.service.RoleCreateService;
import co.prior.iam.module.role.service.RoleDeleteService;
import co.prior.iam.module.role.service.RoleEditService;
import co.prior.iam.module.role.service.RoleInqueryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api")
public class RoleController {

    private final RoleCreateService roleCreateService;


    private final RoleDeleteService roleDeleteService;


    private final RoleEditService roleEditService;


    private final RoleInqueryService roleInqueryService;

    public RoleController(RoleCreateService roleCreateService, RoleDeleteService roleDeleteService, RoleEditService roleEditService, RoleInqueryService roleInqueryService) {
        this.roleCreateService = roleCreateService;
        this.roleDeleteService = roleDeleteService;
        this.roleEditService = roleEditService;
        this.roleInqueryService = roleInqueryService;
    }


    @PostMapping("/role")
    public ResponseEntity<Void> createRole(@RequestBody RoleCreateReq roleCreateReq) {
        log.info("Controller createRole: {}", roleCreateReq );
        roleCreateService.createRole(roleCreateReq);

        return ResponseEntity.created(null).build();

    }
    @DeleteMapping("/role")
    public ResponseEntity<Void> deleteRole(@RequestBody RoleDeleteReq roleDeleteReq) {
        log.info("Controller deleteRole: {}", roleDeleteReq );
        roleDeleteService.deleteRole(roleDeleteReq);


        return ResponseEntity.noContent().build();

    }

    @PutMapping("/role")
    public ResponseEntity<Void> editRole(@RequestBody RoleEditReq roleEditReq) {
        log.info("Controller editRole: {}", roleEditReq );
        roleEditService.editRole(roleEditReq);


        return ResponseEntity.noContent().build();


    }


    @GetMapping("/role/system/{systemId}")
    public ResponseEntity<List<IamMsRole>> inqueryRole(@PathVariable Long systemId) {
        log.info("Controller inqueryRole: {}", systemId );
       List<IamMsRole> list = roleInqueryService.inqueryRole(systemId);

           return ResponseEntity.ok(list);


    }

}
