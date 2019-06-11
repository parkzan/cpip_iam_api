package co.prior.iam.module.role.model.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleEditReq {

    private String systemId;
    private String roleCode;
    private String newName;
    private String newIcon;


}
