package co.prior.iam.error.exception;

import co.prior.iam.model.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataDuplicateException extends RuntimeException {
	
	private final ErrorCode code;

    public DataDuplicateException() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public DataDuplicateException(ErrorCode code) {
        this.code = code;
    }
    
}
