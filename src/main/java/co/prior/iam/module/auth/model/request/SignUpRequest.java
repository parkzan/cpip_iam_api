package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.prior.iam.model.AnswerFlag;
import lombok.Data;
import org.springframework.lang.Nullable;

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

	@NotNull
	private Long userType;

	@Nullable
	private Long surveyId;
	@Nullable
	private Long provinceId;

	private Long lineManager;

	private String department;

	private String division;

	private String unit;

	private String position;
	
}
