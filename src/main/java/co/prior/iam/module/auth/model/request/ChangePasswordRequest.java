package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordRequest {

	private long userId;
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
}
