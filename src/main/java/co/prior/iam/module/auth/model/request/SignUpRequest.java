package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SignUpRequest {

	private long systemId;
	
	@NotBlank
	private String userCode;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private String localFirstName;
	
	private String localMiddleName;
	
	@NotBlank
	private String localLastName;
	
	@NotBlank
	private String engFirstName;
	
	private String engMiddleName;
	
	@NotBlank
	private String engLastName;
	
	@NotBlank
	private String isIamAdmin;
	
}
