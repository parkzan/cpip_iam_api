package co.prior.iam.model;

public enum JwtConstants {

	TOKEN_PREFIX("Bearer "),
	HEADER_STRING("Authorization");
	
	private String value;
	
	private JwtConstants(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}
	
}
