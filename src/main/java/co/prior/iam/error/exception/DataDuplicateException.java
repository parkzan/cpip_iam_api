package co.prior.iam.error.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataDuplicateException extends RuntimeException {
	
	private final String code;
    private final String message;

    public DataDuplicateException(String message) {
        super(message);
        this.code = "99";
        this.message = message;
    }

    public DataDuplicateException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
}
