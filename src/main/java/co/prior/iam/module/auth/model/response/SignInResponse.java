package co.prior.iam.module.auth.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {

	private String accessToken;
	
	@Builder.Default
    private String tokenType = "Bearer";
    
}
