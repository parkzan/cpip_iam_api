package co.prior.iam.module.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRole {

	private long roleId;
	private String roleCode;
	private String roleName;
	
}
