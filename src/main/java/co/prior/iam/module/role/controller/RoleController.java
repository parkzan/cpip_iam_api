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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public BaseApiRespone<RoleRespone> createRole(@RequestBody RoleCreateReq roleCreateReq) throws Exception {
        return roleCreateService.createRole(roleCreateReq);

    }
    @DeleteMapping(path = "/role/deleteRole")
    public BaseApiRespone<RoleRespone> deleteRole(@RequestBody RoleDeleteReq roleDeleteReq)  throws Exception {
        return roleDeleteService.deleteRole(roleDeleteReq);

    }

    @PutMapping(path = "/role/editRole")
    public BaseApiRespone<RoleRespone> editRole(@RequestBody RoleEditReq roleEditReq) throws Exception{
        return roleEditService.editRole(roleEditReq);

    }

    @PostMapping(path = "/role/inqueryRole")
    public BaseApiRespone<List<IamMsRole>> inqueryRole(@RequestBody GetRoleReq getRoleReq) throws Exception {
        return roleInqueryService.inqueryRole(getRoleReq);

    }

}
