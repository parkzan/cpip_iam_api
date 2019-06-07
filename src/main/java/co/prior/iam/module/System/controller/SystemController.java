package co.prior.iam.module.System.controller;


import co.prior.iam.module.System.model.req.SystemAddReq;
import co.prior.iam.module.System.service.SystemCreateService;
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

    @PostMapping(path = "/system/createSystem",consumes = {MediaType.APPLICATION_JSON_VALUE},produces= {MediaType.APPLICATION_JSON_VALUE})
    public String createDorm(@RequestBody SystemAddReq systemAddReq) {
        return systemCreateService.createSystem(systemAddReq);

    }
}
