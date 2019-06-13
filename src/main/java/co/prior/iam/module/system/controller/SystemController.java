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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/system")
public class SystemController {

    @Autowired
    private SystemCreateService systemCreateService;

    @Autowired
    private SystemDeleteService systemDeleteService;

    @Autowired
    private SystemEditService systemEditService;

    @Autowired
    private SystemInquerySystem systemInquerySystem;

    @PostMapping
    public ResponseEntity<SystemRespone> createSystem(@RequestBody SystemAddReq systemAddReq) throws Exception {
        return systemCreateService.createSystem(systemAddReq);

    }

    @DeleteMapping
    public ResponseEntity<SystemRespone> deleteSystem(@RequestBody SystemDeleteReq systemDeleteReq) {
        return systemDeleteService.deleteSystem(systemDeleteReq);

    }

    @PutMapping
    public ResponseEntity<SystemRespone> editSystem(@RequestBody SystemEditReq systemEditReq) {
        return systemEditService.editSystem(systemEditReq);

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
