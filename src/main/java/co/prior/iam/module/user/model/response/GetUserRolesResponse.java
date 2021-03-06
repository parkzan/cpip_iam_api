package co.prior.iam.module.user.model.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserRolesResponse {

	private long systemId;
	private String systemCode;
	private String systemName;
	private long userId;
	private String userCode;
	private String localFirstName;
	private String localMiddleName;
	private String localLastName;
	private String engFirstName;
	private String engMiddleName;
	private String engLastName;
	
	@Builder.Default
	private List<UserRole> userRoles = new ArrayList<>();
	
}
