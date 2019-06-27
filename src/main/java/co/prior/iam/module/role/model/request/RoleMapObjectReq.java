package co.prior.iam.module.role.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleMapObjectReq {

    private Long systemId;
    private Long roleId;
    private List<Long> newObjectId;

}
