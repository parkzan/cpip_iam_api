package co.prior.iam.module.role_object.controller;


import co.prior.iam.module.role_object.model.request.RoleObjectEditReq;
import co.prior.iam.module.role_object.model.respone.RoleMapObjectRespone;
import co.prior.iam.module.role_object.service.GetRoleObjectService;
import co.prior.iam.module.role_object.service.RoleObjectEditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/roleObject")
public class RoleObjectController {


    private GetRoleObjectService getRoleObjectService;
    private RoleObjectEditService roleObjectEditService;

    RoleObjectController(GetRoleObjectService getRoleObjectService , RoleObjectEditService roleObjectEditService){


        this.getRoleObjectService = getRoleObjectService;
        this.roleObjectEditService = roleObjectEditService;

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
    public ResponseEntity<Void> editRoleObject(@RequestBody RoleObjectEditReq roleObjectEditReq) throws Exception{
        log.info("Controller editRoleObject: {}", roleObjectEditReq);
        roleObjectEditService.editRoleObject(roleObjectEditReq);

        return  ResponseEntity.noContent().build();
    }


}
