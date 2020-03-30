package co.prior.iam.module.user.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.prior.iam.model.AnswerFlag;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
	
	private AnswerFlag disableFlag;

	private Long userType;

	private Long surveyId;

	private Long provinceId;

	private Long LineManager;

	private String department;

	private String division;

	private String unit;

	private String position;


}
