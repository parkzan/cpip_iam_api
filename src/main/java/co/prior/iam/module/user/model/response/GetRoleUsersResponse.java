package co.prior.iam.module.user.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetRoleUsersResponse {

	private long userRoleId;
	private long systemId;
	private String systemCode;
	private String systemName;
	private long roleId;
	private String roleCode;
	private String roleName;
	
	@Builder.Default
	private List<UserData> users = new ArrayList<>();
	
}
