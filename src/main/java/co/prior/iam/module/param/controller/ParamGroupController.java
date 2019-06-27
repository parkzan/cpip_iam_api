package co.prior.iam.module.param.controller;


import co.prior.iam.entity.IamMsParameterGroup;
import co.prior.iam.module.param.service.ParamGroupInquiryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParamGroupController {

    ParamGroupInquiryService paramGroupInquiryService;

    public ParamGroupController(ParamGroupInquiryService paramGroupInquiryService){
        this.paramGroupInquiryService = paramGroupInquiryService;
    }


    @GetMapping("/paramGroups")
    public ResponseEntity<List<IamMsParameterGroup>> inquiryParamGroup() throws Exception{
        return ResponseEntity.ok(paramGroupInquiryService.inquiryParamGroup());
    }



}
