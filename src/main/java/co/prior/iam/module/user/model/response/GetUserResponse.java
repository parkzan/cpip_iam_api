package co.prior.iam.module.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserResponse {

	private long systemId;
	private long userId;
	private String userCode;
	private String localFirstName;
	private String localMiddleName;
	private String localLastName;
	private String engFirstName;
	private String engMiddleName;
	private String engLastName;
	private String isIamAdmin;
	
}
