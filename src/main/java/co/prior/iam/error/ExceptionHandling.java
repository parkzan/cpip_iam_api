package co.prior.iam.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.prior.iam.model.ErrorModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<ErrorModel> handleDataNotFoundException(DataNotFoundException ex) {
		log.error(ex.getMessage(), ex);
		
		ErrorModel errorModel = new ErrorModel(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataDuplicateException.class)
	public final ResponseEntity<ErrorModel> handleDataDuplicateException(DataDuplicateException ex) {
		log.error(ex.getMessage(), ex);

		ErrorModel errorModel = new ErrorModel(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(errorModel, HttpStatus.CONFLICT);
	}
}