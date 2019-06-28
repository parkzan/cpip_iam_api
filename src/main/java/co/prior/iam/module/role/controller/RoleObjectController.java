package co.prior.iam.module.role.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.module.role.model.request.RoleMapObjectReq;
import co.prior.iam.module.role.model.respone.RoleMapObjectRespone;
import co.prior.iam.module.role.service.GetRoleObjectService;
import co.prior.iam.module.role.service.RoleMapObjectService;
import lombok.extern.slf4j.Slf4j;

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
    public ResponseEntity<RoleMapObjectRespone> getRoleObject(@PathVariable Long systemId,@PathVariable Long roleId) {
        log.info("Controller getRoleObject: {}", roleId);
        RoleMapObjectRespone list = getRoleObjectService.getRoleObject(systemId,roleId);

        return ResponseEntity.ok(list);


    }


    @GetMapping("/roleObjects/system/{systemId}")
    public ResponseEntity<List<RoleMapObjectRespone>> getAllRoleObject(@PathVariable Long systemId) {
        log.info("Controller getAllRoleObject: {}", systemId);
        List<RoleMapObjectRespone> list = getRoleObjectService.allRoleMapObject(systemId);

        return ResponseEntity.ok(list);


    }

    @PostMapping("/roleObject")
    public ResponseEntity<Void> editRoleObject(@RequestBody RoleMapObjectReq roleMapObjectReq) {
        log.info("Controller editRoleObject: {}", roleMapObjectReq);
        roleMapObjectService.editRoleObject(roleMapObjectReq);

        return  ResponseEntity.noContent().build();
    }


}
