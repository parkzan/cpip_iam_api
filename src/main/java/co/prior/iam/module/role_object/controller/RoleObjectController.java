package co.prior.iam.module.role_object.controller;


import co.prior.iam.module.role_object.model.request.RoleMapObjectReq;
import co.prior.iam.module.role_object.model.respone.RoleMapObjectRespone;
import co.prior.iam.module.role_object.service.GetRoleObjectService;
import co.prior.iam.module.role_object.service.RoleMapObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/roleObject")
public class RoleObjectController {


    private GetRoleObjectService getRoleObjectService;
    private RoleMapObjectService roleMapObjectService;

    RoleObjectController(GetRoleObjectService getRoleObjectService , RoleMapObjectService roleMapObjectService){


        this.getRoleObjectService = getRoleObjectService;
        this.roleMapObjectService = roleMapObjectService;

    }



    @GetMapping
    @RequestMapping("/{systemId}/{roleId}")
    public ResponseEntity<RoleMapObjectRespone> getRoleObject(@PathVariable Long systemId,@PathVariable Long roleId) throws Exception {
        log.info("Controller getRoleObject: {}", roleId);
        RoleMapObjectRespone list = getRoleObjectService.getRoleObject(systemId,roleId);

        return ResponseEntity.ok(list);


    }

    @GetMapping
    @RequestMapping("/{systemId}")
    public ResponseEntity<List<RoleMapObjectRespone>> getAllRoleObject(@PathVariable Long systemId) throws Exception {
        log.info("Controller getAllRoleObject: {}", systemId);
        List<RoleMapObjectRespone> list = getRoleObjectService.allRoleMapObject(systemId);

        return ResponseEntity.ok(list);


    }

    @PostMapping
    public ResponseEntity<Void> editRoleObject(@RequestBody RoleMapObjectReq roleMapObjectReq) throws Exception{
        log.info("Controller editRoleObject: {}", roleMapObjectReq);
        roleMapObjectService.editRoleObject(roleMapObjectReq);

        return  ResponseEntity.noContent().build();
    }


}
