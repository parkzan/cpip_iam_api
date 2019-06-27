package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivateUserRequest {
	
	private long systemId;
	
	@NotBlank
	private String userCode;
	
	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
}
