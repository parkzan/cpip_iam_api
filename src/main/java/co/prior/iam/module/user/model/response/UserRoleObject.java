package co.prior.iam.module.user.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRoleObject {

	private String name;
	
	private List<UserObject> objects;
	
}
