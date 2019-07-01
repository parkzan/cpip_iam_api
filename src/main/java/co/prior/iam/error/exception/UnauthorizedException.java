package co.prior.iam.error.exception;

import co.prior.iam.model.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UnauthorizedException extends RuntimeException {

	private final ErrorCode code;

	public UnauthorizedException() {
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
	}

	public UnauthorizedException(ErrorCode code) {
		this.code = code;
	}
	
}
