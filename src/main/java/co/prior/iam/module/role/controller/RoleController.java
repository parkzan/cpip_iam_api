package co.prior.iam.module.role.controller;


import co.prior.iam.common.BaseApiRespone;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.module.role.model.req.GetRoleReq;
import co.prior.iam.module.role.model.req.RoleCreateReq;
import co.prior.iam.module.role.model.req.RoleDeleteReq;
import co.prior.iam.module.role.model.req.RoleEditReq;
import co.prior.iam.module.role.model.res.RoleRespone;
import co.prior.iam.module.role.service.RoleCreateService;
import co.prior.iam.module.role.service.RoleDeleteService;
import co.prior.iam.module.role.service.RoleEditService;
import co.prior.iam.module.role.service.RoleInqueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RoleController {
    @Autowired
    RoleCreateService roleCreateService;

    @Autowired
    RoleDeleteService roleDeleteService;

    @Autowired
    RoleEditService roleEditService;

    @Autowired
    RoleInqueryService roleInqueryService;


    @PostMapping(path = "/role/createRole")
    public ResponseEntity<RoleRespone> createRole(@RequestBody RoleCreateReq roleCreateReq) throws Exception {
        return roleCreateService.createRole(roleCreateReq);

    }
    @DeleteMapping(path = "/role/deleteRole")
    public ResponseEntity<RoleRespone> deleteRole(@RequestBody RoleDeleteReq roleDeleteReq)  throws Exception {
        return roleDeleteService.deleteRole(roleDeleteReq);

    }

    @PutMapping(path = "/role/editRole")
    public ResponseEntity<RoleRespone> editRole(@RequestBody RoleEditReq roleEditReq) throws Exception{
        return roleEditService.editRole(roleEditReq);

    }

    @PostMapping(path = "/role/inqueryRole")
    public ResponseEntity<List<IamMsRole>> inqueryRole(@RequestBody GetRoleReq getRoleReq) throws Exception {
       Optional<List<IamMsRole>> list = roleInqueryService.inqueryRole(getRoleReq);
       if (list.isPresent()){
           return new ResponseEntity<>(list.get(), HttpStatus.OK);
       }
            return new ResponseEntity<>(list.get(), HttpStatus.NOT_FOUND);
    }

}
