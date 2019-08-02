package co.prior.iam.module.user.model.response;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetUserRoleSystemRespone {


    private long userRoleId;
    private long roleId;
    private String roleCode;
    private String roleName;
    private long userId;
    private String userCode;
    private String localFirstName;
    private String localMiddleName;
    private String localLastName;
    private String engFirstName;
    private String engMiddleName;
    private String engLastName;
}
