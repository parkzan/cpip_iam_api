package co.prior.iam.module.role_object.model.respone;

import co.prior.iam.entity.IamMsObject;
import co.prior.iam.entity.IamMsRole;
import co.prior.iam.entity.IamMsSystem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleMapObjectRespone {

    private IamMsSystem iamMsSystem;
    private IamMsRole iamMsRole;
    private IamMsObject iamMsObject;
}
