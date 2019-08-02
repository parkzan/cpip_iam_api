package co.prior.iam.module.user.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetUserResponse {

	private long userId;
	private String userCode;
	private String localFirstName;
	private String localMiddleName;
	private String localLastName;
	private String engFirstName;
	private String engMiddleName;
	private String engLastName;
	private String isIamAdmin;
	private String disableFlag;
	private LocalDateTime createdDate;
	private String createdBy;
	private LocalDateTime updatedDate;
	private String updatedBy;
	
}
