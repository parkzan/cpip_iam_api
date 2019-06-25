package co.prior.iam.module.role_object.controller;


import co.prior.iam.entity.IamMsRoleObject;
import co.prior.iam.module.role_object.model.request.RoleMapObjectCreateReq;
import co.prior.iam.module.role_object.model.request.RoleObjectEditReq;
import co.prior.iam.module.role_object.model.respone.RoleMapObjectRespone;
import co.prior.iam.module.role_object.service.GetRoleObjectService;
import co.prior.iam.module.role_object.service.RoleObjectCreateService;
import co.prior.iam.module.role_object.service.RoleObjectEditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/roleObject")
public class RoleObjectController {

    private RoleObjectCreateService roleObjectCreateService;
    private GetRoleObjectService getRoleObjectService;
    private RoleObjectEditService roleObjectEditService;

    RoleObjectController(RoleObjectCreateService roleObjectCreateService,GetRoleObjectService getRoleObjectService , RoleObjectEditService roleObjectEditService){

        this.roleObjectCreateService = roleObjectCreateService;
        this.getRoleObjectService = getRoleObjectService;
        this.roleObjectEditService = roleObjectEditService;

    }

    @PostMapping
    public ResponseEntity<Void> createRoleObject(@RequestBody RoleMapObjectCreateReq roleMapObjectCreateReq) throws Exception {

        roleObjectCreateService.createRoleMapObject(roleMapObjectCreateReq);

        return ResponseEntity.created(null).build();

    }

    @GetMapping
    @RequestMapping(path = "/{systemId}/{roleId}")
    public ResponseEntity<RoleMapObjectRespone> getRoleObject(@PathVariable Long systemId,@PathVariable Long roleId) throws Exception {
        RoleMapObjectRespone list = getRoleObjectService.getRoleObject(systemId,roleId);

        return ResponseEntity.ok(list);


    }

    @GetMapping
    @RequestMapping(path = "/{systemId}")
    public ResponseEntity<List<RoleMapObjectRespone>> getAllRoleObject(@PathVariable Long systemId) throws Exception {
        List<RoleMapObjectRespone> list = getRoleObjectService.allRoleMapObject(systemId);

        return ResponseEntity.ok(list);


    }

    @PutMapping
    public ResponseEntity<Void> editRoleObject(@RequestBody RoleObjectEditReq roleObjectEditReq) throws Exception{

        roleObjectEditService.editRoleObject(roleObjectEditReq);

        return  ResponseEntity.noContent().build();
    }


}
