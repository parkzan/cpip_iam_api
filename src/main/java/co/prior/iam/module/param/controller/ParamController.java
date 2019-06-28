package co.prior.iam.module.param.controller;


import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.module.param.model.ParamRespone;
import co.prior.iam.module.param.service.ParamGroupInquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParamController {

    ParamGroupInquiryService paramGroupInquiryService;

    public ParamController(ParamGroupInquiryService paramGroupInquiryService){
        this.paramGroupInquiryService = paramGroupInquiryService;
    }


    @PostMapping("/paramGroups")
    public ResponseEntity<ParamRespone> inquiryParamGroup(@RequestBody String paramGroup) throws Exception{
        return ResponseEntity.ok(paramGroupInquiryService.inquiryParamGroup(paramGroup));
    }



}
