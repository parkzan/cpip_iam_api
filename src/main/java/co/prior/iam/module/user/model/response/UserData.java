package co.prior.iam.module.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserData {

	private long userId;
	private String userCode;
	
}
