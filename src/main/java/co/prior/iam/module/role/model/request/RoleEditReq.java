package co.prior.iam.module.role.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleEditReq {

    private long systemId;
    private String roleCode;
    private String newName;
    private String newIcon;

}
