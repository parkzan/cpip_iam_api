package co.prior.iam.module.System.controller;


import co.prior.iam.entity.SystemEntity;
import co.prior.iam.module.System.model.req.SystemAddReq;
import co.prior.iam.module.System.model.req.SystemDeleteReq;
import co.prior.iam.module.System.model.req.SystemEditReq;
import co.prior.iam.module.System.service.SystemCreateService;
import co.prior.iam.module.System.service.SystemDeleteService;
import co.prior.iam.module.System.service.SystemEditService;
import co.prior.iam.module.System.service.SystemInquerySystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SystemController {

    @Autowired
    private SystemCreateService systemCreateService;

    @Autowired
    private SystemDeleteService systemDeleteService;

    @Autowired
    private SystemEditService systemEditService;

    @Autowired
    private SystemInquerySystem systemInquerySystem;

    @PostMapping(path = "/system/createSystem",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String createDorm(@RequestBody SystemAddReq systemAddReq) {
        return systemCreateService.createSystem(systemAddReq);

    }

    @PostMapping(path = "/system/deleteSystem",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String createDorm(@RequestBody SystemDeleteReq systemDeleteReq) {
        return systemDeleteService.deleteSystem(systemDeleteReq);

    }

    @PostMapping(path = "/system/editSystem",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String createDorm(@RequestBody SystemEditReq systemEditReq) {
        return systemEditService.editSystem(systemEditReq);

    }

    @GetMapping(path = "/system/inquerySystem",produces= {MediaType.APPLICATION_JSON_VALUE})
    public List<SystemEntity> inquerySystem()  {
        return  systemInquerySystem.inquerySystem();
    }


}
