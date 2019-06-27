package co.prior.iam.error.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -8372278667837617582L;
	
	private final String code;
	private final String message;

	public DataNotFoundException(String message) {
		super(message);
		this.code = "99";
		this.message = message;
	}

	public DataNotFoundException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
}
