package co.prior.iam.error.exception;

import co.prior.iam.model.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataNotFoundException extends RuntimeException {
	
	private final ErrorCode code;

	public DataNotFoundException() {
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
	}

	public DataNotFoundException(ErrorCode code) {
		this.code = code;
	}
	
}
