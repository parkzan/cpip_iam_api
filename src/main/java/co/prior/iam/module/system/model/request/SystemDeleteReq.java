package co.prior.iam.module.system.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemDeleteReq {

    private String systemCode;
    
}
