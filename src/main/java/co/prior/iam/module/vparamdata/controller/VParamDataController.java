package co.prior.iam.module.vparamdata.controller;


import co.prior.iam.module.vparamdata.model.respone.VparamDataReponse;
import co.prior.iam.module.vparamdata.service.VParamDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VParamDataController {

    private final VParamDataService paramDataService;

    public VParamDataController(VParamDataService paramDataService) {
        this.paramDataService = paramDataService;
    }

    @GetMapping("vparam")
    public ResponseEntity<List<VparamDataReponse>> getMenuParam(){

        return ResponseEntity.ok(this.paramDataService.getMenuObject());
    }
}
