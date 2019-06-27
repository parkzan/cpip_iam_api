package co.prior.iam.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.error.exception.ForbiddenException;
import co.prior.iam.error.exception.UnauthorizedException;
import co.prior.iam.model.ErrorModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

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
	
	@ExceptionHandler(value = UnauthorizedException.class)
	protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
		logger.error(ex.getMessage(), ex);
		
		ErrorModel errorModel = new ErrorModel(ex.getCode(), ex.getMessage());
		
		return new ResponseEntity<>(errorModel, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = ForbiddenException.class)
	protected ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
		logger.error(ex.getMessage(), ex);

		ErrorModel errorModel = new ErrorModel(ex.getCode(), ex.getMessage());

		return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<Object> handleInternalServerError(Exception ex) {
		logger.error(ex.getLocalizedMessage(), ex);

		ErrorModel errorModel = new ErrorModel("99", "internal server error");

		return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
