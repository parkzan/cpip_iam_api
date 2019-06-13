package co.prior.iam.module.role.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleCreateReq {

    private Long systemId;
    private String roleName;
    private String roleCode;
    private String roleIcon;
}
