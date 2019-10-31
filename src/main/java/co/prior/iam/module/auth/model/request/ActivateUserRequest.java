package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivateUserRequest {

	
	@NotBlank
	private String userCode;
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;

	@NotBlank
	private String confirmPassword;
	
}
