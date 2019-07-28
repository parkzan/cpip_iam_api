package co.prior.iam.module.user.model.response;


import co.prior.iam.entity.IamMsUserRole;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserRoleSystemRespone {

    long systemId;
    String systemCode;
    String systemName;
    String systemIcon;

    List<UserRole> userRoles;
}
