package co.prior.iam.module.user.model.response;

import java.util.List;

import co.prior.iam.entity.IamMsObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRoleObject {

	private String roleCode;
	
	private List<UserObject> objects;
	
}
