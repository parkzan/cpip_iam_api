package co.prior.iam.module.role.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRoleObjectReq {

    private Long systemId;
    private Long roleId;


}
