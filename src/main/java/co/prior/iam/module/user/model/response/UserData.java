package co.prior.iam.module.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {

	private long userRoleId;
	private long userId;
	private String userCode;
	private String localFirstName;
	private String localMiddleName;
	private String localLastName;
	private String engFirstName;
	private String engMiddleName;
	private String engLastName;
	
}
