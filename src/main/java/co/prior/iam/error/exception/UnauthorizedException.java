package co.prior.iam.error.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 5279618793746873570L;

	private final String code;
	private final String message;

	public UnauthorizedException(String message) {
		super(message);
		this.code = "99";
		this.message = message;
	}

	public UnauthorizedException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
}
