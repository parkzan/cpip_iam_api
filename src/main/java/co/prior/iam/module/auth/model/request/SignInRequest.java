package co.prior.iam.module.auth.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import co.prior.iam.model.AnswerFlag;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInRequest {

	@NotBlank
	private String userCode;
	
	@NotBlank
	private String password;
	
	private AnswerFlag isIamAdmin;
	
}
