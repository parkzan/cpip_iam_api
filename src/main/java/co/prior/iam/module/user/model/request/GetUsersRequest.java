package co.prior.iam.module.user.model.request;

import co.prior.iam.model.PageableRequest;
import lombok.Data;

@Data
public class GetUsersRequest {

	private long systemId;
	private PageableRequest pageable;
	
}
