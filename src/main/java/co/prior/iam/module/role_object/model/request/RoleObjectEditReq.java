package co.prior.iam.module.role_object.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleObjectEditReq {

    private Long systemId;
    private Long roleId;
    private Long[] newObjectId;

}
