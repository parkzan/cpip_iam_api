package co.prior.iam.error.exception;

import co.prior.iam.model.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {

	private final ErrorCode code;

    public BadRequestException() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public BadRequestException(ErrorCode code) {
        this.code = code;
    }
    
}
