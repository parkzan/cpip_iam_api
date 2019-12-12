package co.prior.iam.module.user.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserObject {

	private Long objectId;
	private String objectCode;
	private String objectName;
	private Long objectParentId;
	private List<UserObject> objects;
	private String objectUrl;
	private Float sort;

	
}
