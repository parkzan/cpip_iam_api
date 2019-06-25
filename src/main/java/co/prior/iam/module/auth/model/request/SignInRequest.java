package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignInRequest {

	@NotBlank
	private String userCode;
	
	@NotBlank
	private String password;
	
}
