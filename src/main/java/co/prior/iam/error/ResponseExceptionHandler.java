package co.prior.iam.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.prior.iam.error.exception.BadRequestException;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.error.exception.ForbiddenException;
import co.prior.iam.error.exception.UnauthorizedException;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.model.ErrorModel;
import co.prior.iam.module.param.model.response.ParamInfoData;
import co.prior.iam.module.param.service.ParamService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	private final ParamService paramService;
	
	public ResponseExceptionHandler(ParamService paramService) {
		this.paramService = paramService;
	}
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<ErrorModel> handleBadRequestException(BadRequestException ex) {
		log.error(ex.getCode().toString(), ex);
		
		ParamInfoData paramInfo = this.getErrorMessage(ex.getCode());
		ErrorModel errorModel = new ErrorModel(ex.getCode().code(), paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());
		
		return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<ErrorModel> handleDataNotFoundException(DataNotFoundException ex) {
		log.error(ex.getCode().toString(), ex);
		
		ParamInfoData paramInfo = this.getErrorMessage(ex.getCode());
		ErrorModel errorModel = new ErrorModel(ex.getCode().code(), paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());
		
		return new ResponseEntity<>(errorModel, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DataDuplicateException.class)
	public final ResponseEntity<ErrorModel> handleDataDuplicateException(DataDuplicateException ex) {
		log.error(ex.getCode().toString(), ex);

		ParamInfoData paramInfo = this.getErrorMessage(ex.getCode());

		log.info("param info : {}" , paramInfo);
		ErrorModel errorModel = new ErrorModel(ex.getCode().code(), paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());
		
		return new ResponseEntity<>(errorModel, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = UnauthorizedException.class)
	protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
		logger.error(ex.getCode().toString(), ex);
		
		ParamInfoData paramInfo = this.getErrorMessage(ex.getCode());
		ErrorModel errorModel = new ErrorModel(ex.getCode().code(), paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());
		
		return new ResponseEntity<>(errorModel, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = ForbiddenException.class)
	protected ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
		logger.error(ex.getCode().toString(), ex);
		
		ParamInfoData paramInfo = this.getErrorMessage(ex.getCode());
		ErrorModel errorModel = new ErrorModel(ex.getCode().code(), paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());
		
		return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	protected ResponseEntity<Object> handleForbiddenException(AccessDeniedException ex) {
		logger.error(ex.getLocalizedMessage(), ex);

		ParamInfoData paramInfo = this.getErrorMessage(ErrorCode.ACCESS_DENIED);
		ErrorModel errorModel = new ErrorModel(ErrorCode.ACCESS_DENIED.code(), paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());

		return new ResponseEntity<>(errorModel, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = Exception.class)
	protected ResponseEntity<Object> handleInternalServerError(Exception ex) {
		logger.error(ex.getLocalizedMessage(), ex);

		ParamInfoData paramInfo = this.getErrorMessage(ErrorCode.INTERNAL_SERVER_ERROR);
		ErrorModel errorModel = new ErrorModel(ErrorCode.INTERNAL_SERVER_ERROR.code(), paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());

		return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ParamInfoData getErrorMessage(ErrorCode code) {
		return this.paramService.getErrorMessage(code)
				.orElse(ParamInfoData.builder()
						.paramEnMessage("System error")
						.paramLocalMessage("System error")
						.build());
	}
	
}
