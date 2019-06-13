package co.prior.iam.module.system.controller;



import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.module.system.model.request.SystemAddReq;
import co.prior.iam.module.system.model.request.SystemDeleteReq;
import co.prior.iam.module.system.model.request.SystemEditReq;
import co.prior.iam.module.system.model.respone.SystemRespone;
import co.prior.iam.module.system.service.SystemCreateService;
import co.prior.iam.module.system.service.SystemDeleteService;
import co.prior.iam.module.system.service.SystemEditService;
import co.prior.iam.module.system.service.SystemInquerySystem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/system")
public class SystemController {


    private final SystemCreateService systemCreateService;


    private final SystemDeleteService systemDeleteService;


    private final SystemEditService systemEditService;


    private final SystemInquerySystem systemInquerySystem;

    public SystemController(SystemCreateService systemCreateService, SystemDeleteService systemDeleteService, SystemEditService systemEditService, SystemInquerySystem systemInquerySystem) {
        this.systemCreateService = systemCreateService;
        this.systemDeleteService = systemDeleteService;
        this.systemEditService = systemEditService;
        this.systemInquerySystem = systemInquerySystem;
    }

    @PostMapping
    public ResponseEntity<SystemRespone> createSystem(@RequestBody SystemAddReq systemAddReq) throws Exception {

        systemCreateService.createSystem(systemAddReq);
        SystemRespone respone = new SystemRespone();
        respone.setCode("S");
        respone.setMessage("success");
        return new ResponseEntity<>(respone,HttpStatus.CREATED);

    }

    @DeleteMapping
    public ResponseEntity<SystemRespone> deleteSystem(@RequestBody SystemDeleteReq systemDeleteReq) throws Exception  {

        systemDeleteService.deleteSystem(systemDeleteReq);
        SystemRespone respone = new SystemRespone();
        respone.setCode("S");
        respone.setMessage("success");
        return new ResponseEntity<>(respone,HttpStatus.OK);

    }

    @PutMapping
    public ResponseEntity<SystemRespone> editSystem(@RequestBody SystemEditReq systemEditReq) throws Exception {

        systemEditService.editSystem(systemEditReq);
        SystemRespone respone = new SystemRespone();
        respone.setCode("S");
        respone.setMessage("success");
        return new ResponseEntity<>(respone,HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<IamMsSystem>> inquerySystem()  {
        Optional<List<IamMsSystem>> list = systemInquerySystem.inquerySystem();

        if (list.isPresent()){
            return new ResponseEntity<>(list.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(list.get(),HttpStatus.NOT_FOUND);
    }


}
