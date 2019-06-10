package co.prior.iam.module.role.controller;


import co.prior.iam.module.role.model.req.RoleCreateReq;
import co.prior.iam.module.role.model.req.RoleDeleteReq;
import co.prior.iam.module.role.model.req.RoleEditReq;
import co.prior.iam.module.role.service.RoleCreateService;
import co.prior.iam.module.role.service.RoleDeleteService;
import co.prior.iam.module.role.service.RoleEditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    RoleCreateService roleCreateService;

    @Autowired
    RoleDeleteService roleDeleteService;

    @Autowired
    RoleEditService roleEditService;


    @PostMapping(path = "/role/createRole",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String createRole(@RequestBody RoleCreateReq roleCreateReq) {
        return roleCreateService.createRole(roleCreateReq);

    }
    @PostMapping(path = "/role/deleteRole",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String deleteRole(@RequestBody RoleDeleteReq roleDeleteReq) {
        return roleDeleteService.deleteRole(roleDeleteReq);

    }

    @PostMapping(path = "/role/editRole",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String editRole(@RequestBody RoleEditReq roleEditReq) {
        return roleEditService.editRole(roleEditReq);

    }

}
