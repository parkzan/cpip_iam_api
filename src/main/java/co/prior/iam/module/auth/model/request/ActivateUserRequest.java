package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ActivateUserRequest {
	
	private long systemId;
	
	@NotBlank
	private String userCode;
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
}
