package co.prior.iam.module.user.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.prior.iam.model.PageableRequest;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUsersRequest {

	private long systemId;
	private PageableRequest pageable;
	
}
