package co.prior.iam.module.param.controller;


import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.module.param.model.ParamInfoCreateModel;
import co.prior.iam.module.param.model.ParamRespone;
import co.prior.iam.module.param.service.ParamGroupInquiryService;
import co.prior.iam.module.param.service.ParamInfoCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParamController {

    ParamGroupInquiryService paramGroupInquiryService;
    ParamInfoCreateService paramInfoCreateService;

    public ParamController(ParamGroupInquiryService paramGroupInquiryService ,ParamInfoCreateService paramInfoCreateService){
        this.paramGroupInquiryService = paramGroupInquiryService;
        this.paramInfoCreateService = paramInfoCreateService;
    }


    @GetMapping("/paramGroups")
    public ResponseEntity<ParamRespone> inquiryParamGroup(@RequestParam String paramGroup) throws Exception{
        return ResponseEntity.ok(paramGroupInquiryService.inquiryParamGroup(paramGroup));
    }

    @PostMapping("/paramGroups")
    public ResponseEntity<Void> create(@RequestBody ParamInfoCreateModel paramInfoCreateModel) throws Exception{
        paramInfoCreateService.createParamInfo(paramInfoCreateModel);

        return ResponseEntity.created(null).build();
    }



}
