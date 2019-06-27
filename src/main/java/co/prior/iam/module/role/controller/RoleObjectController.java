package co.prior.iam.module.role.controller;


import co.prior.iam.module.role.model.request.RoleMapObjectReq;
import co.prior.iam.module.role.model.respone.RoleMapObjectRespone;
import co.prior.iam.module.role.service.GetRoleObjectService;
import co.prior.iam.module.role.service.RoleMapObjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api")
public class RoleObjectController {


    private GetRoleObjectService getRoleObjectService;
    private RoleMapObjectService roleMapObjectService;

    RoleObjectController(GetRoleObjectService getRoleObjectService , RoleMapObjectService roleMapObjectService){


        this.getRoleObjectService = getRoleObjectService;
        this.roleMapObjectService = roleMapObjectService;

    }


    @GetMapping("/roleObject/system/{systemId}/role/{roleId}")
    public ResponseEntity<RoleMapObjectRespone> getRoleObject(@PathVariable Long systemId,@PathVariable Long roleId) throws Exception {
        log.info("Controller getRoleObject: {}", roleId);
        RoleMapObjectRespone list = getRoleObjectService.getRoleObject(systemId,roleId);

        return ResponseEntity.ok(list);


    }


    @GetMapping("/roleObjects/system/{systemId}")
    public ResponseEntity<List<RoleMapObjectRespone>> getAllRoleObject(@PathVariable Long systemId) throws Exception {
        log.info("Controller getAllRoleObject: {}", systemId);
        List<RoleMapObjectRespone> list = getRoleObjectService.allRoleMapObject(systemId);

        return ResponseEntity.ok(list);


    }

    @PostMapping("/roleObject")
    public ResponseEntity<Void> editRoleObject(@RequestBody RoleMapObjectReq roleMapObjectReq) throws Exception{
        log.info("Controller editRoleObject: {}", roleMapObjectReq);
        roleMapObjectService.editRoleObject(roleMapObjectReq);

        return  ResponseEntity.noContent().build();
    }


}
