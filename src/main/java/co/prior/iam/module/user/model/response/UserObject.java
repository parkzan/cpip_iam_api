package co.prior.iam.module.user.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserObject {

	private String objectCode;
	private String objectName;
	private List<UserObject> objects;

	
}
