package co.prior.iam.module.System.controller;


import co.prior.iam.module.System.model.req.SystemAddReq;
import co.prior.iam.module.System.model.req.SystemDeleteReq;
import co.prior.iam.module.System.service.SystemCreateService;
import co.prior.iam.module.System.service.SystemDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemController {

    @Autowired
    private SystemCreateService systemCreateService;

    @Autowired
    private SystemDeleteService systemDeleteService;

    @PostMapping(path = "/system/createSystem",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String createDorm(@RequestBody SystemAddReq systemAddReq) {
        return systemCreateService.createSystem(systemAddReq);

    }

    @PostMapping(path = "/system/deleteSystem",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String createDorm(@RequestBody SystemDeleteReq systemDeleteReq) {
        return systemDeleteService.deleteSystem(systemDeleteReq);

    }
}
