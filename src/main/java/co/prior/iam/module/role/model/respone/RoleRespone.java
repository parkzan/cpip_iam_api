package co.prior.iam.module.role.model.respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleRespone {

    private String message;
    private String code;
    
}
