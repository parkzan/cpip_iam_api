package co.prior.iam.module.auth.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {

	private String accessToken;
	
	@Builder.Default
    private String tokenType = "Bearer";
	
	private long expiresIn;
	private long expiresAt;
	private String refreshToken;
	private long refreshExpiresIn;
	private long refreshExpiresAt;

    
}
