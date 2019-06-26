package co.prior.iam.error;

import co.prior.iam.model.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<ErrorModel> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorModel errorModel = new ErrorModel(404, ex.getMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(DataDuplicateException.class)
    public final ResponseEntity<ErrorModel> handleDataDuplicateException(DataDuplicateException ex){
        ErrorModel errorModel = new ErrorModel(409, ex.getMessage());
        return new ResponseEntity<>(errorModel, HttpStatus.CONFLICT);
    }
}