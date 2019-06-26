package co.prior.iam.error;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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
