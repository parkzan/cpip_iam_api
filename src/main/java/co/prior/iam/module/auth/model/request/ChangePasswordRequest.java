package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangePasswordRequest {

	private long userId;
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
}
