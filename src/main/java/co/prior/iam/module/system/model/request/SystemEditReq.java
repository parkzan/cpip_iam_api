package co.prior.iam.module.system.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemEditReq {
	
    private String systemCode;
    private String newName;
    private String newIcon;
    
}
