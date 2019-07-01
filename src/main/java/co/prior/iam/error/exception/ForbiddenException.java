package co.prior.iam.error.exception;

import co.prior.iam.model.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ForbiddenException extends RuntimeException {
	
	private final ErrorCode code;

	public ForbiddenException() {
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
	}

	public ForbiddenException(ErrorCode code) {
		this.code = code;
	}
	
}
