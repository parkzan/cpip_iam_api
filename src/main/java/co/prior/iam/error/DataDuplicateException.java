package co.prior.iam.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DataDuplicateException extends RuntimeException {

    public DataDuplicateException(String exception){
        super(exception);
    }
}
