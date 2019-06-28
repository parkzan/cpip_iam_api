package co.prior.iam.error.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class BadRequestException extends RuntimeException {

	private final String code;
    private final String message;

    public BadRequestException(String message) {
        super(message);
        this.code = "99";
        this.message = message;
    }

    public BadRequestException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
}
