package co.prior.iam.module.role_object.model.respone;

import co.prior.iam.entity.IamMsRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleMapRespone {
    private IamMsRole role;
    private  ObjectMapRespone object;
}
