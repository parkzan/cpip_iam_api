package co.prior.iam.module.user.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IamMsUserPage {

	private int page;
	private int size;
	private int totalPages;
	private long totalRecords;
	private boolean isFirst;
	private boolean isLast;
	private List<GetUserResponse> data;

}
