package co.prior.iam.module.user.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class EditUserRequest {

	private long userId;
	
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
	
}
