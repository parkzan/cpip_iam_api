package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.prior.iam.model.AnswerFlag;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequest {


	
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
	
	private AnswerFlag isIamAdmin;


	private Long userType;

	private Long surveyId;

	private Long provinceId;
	
}
