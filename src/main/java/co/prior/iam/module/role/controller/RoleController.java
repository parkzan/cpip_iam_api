package co.prior.iam.module.role.controller;


import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.request.RoleCreateReq;
import co.prior.iam.module.role.model.request.RoleDeleteReq;
import co.prior.iam.module.role.model.request.RoleEditReq;
import co.prior.iam.module.role.model.respone.RoleRespone;
import co.prior.iam.module.role.service.RoleCreateService;
import co.prior.iam.module.role.service.RoleDeleteService;
import co.prior.iam.module.role.service.RoleEditService;
import co.prior.iam.module.role.service.RoleInqueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/role")
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


    @PostMapping
    public ResponseEntity<RoleRespone> createRole(@RequestBody RoleCreateReq roleCreateReq) throws Exception {

        roleCreateService.createRole(roleCreateReq);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @DeleteMapping
    public ResponseEntity<RoleRespone> deleteRole(@RequestBody RoleDeleteReq roleDeleteReq)  throws Exception {

        roleDeleteService.deleteRole(roleDeleteReq);


        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<RoleRespone> editRole(@RequestBody RoleEditReq roleEditReq) throws Exception{

        roleEditService.editRole(roleEditReq);


        return new ResponseEntity<>(HttpStatus.OK);


    }

    @GetMapping
    @RequestMapping(path = "/{systemId}")
    public ResponseEntity<List<IamMsRole>> inqueryRole(@PathVariable Long systemId) throws Exception {
       List<IamMsRole> list = roleInqueryService.inqueryRole(systemId);

           return new ResponseEntity<>(list, HttpStatus.OK);


    }

}
