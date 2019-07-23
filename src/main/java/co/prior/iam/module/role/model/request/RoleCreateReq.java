package co.prior.iam.module.role.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleCreateReq {

    private long systemId;
    private String roleName;
    private String roleCode;
    private String roleIcon;
    
}
