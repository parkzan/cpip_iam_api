package co.prior.iam.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.prior.iam.model.ErrorCode;
import co.prior.iam.model.ErrorModel;
import co.prior.iam.module.param.model.response.ParamInfoData;
import co.prior.iam.module.param.service.ParamService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ParamService paramService;
	
	public JwtAuthenticationEntryPoint(ParamService paramService) {
		this.paramService = paramService;
	}
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error(e.getMessage(), e);
        
        ParamInfoData paramInfo = this.getErrorMessage(ErrorCode.ACCESS_DENIED);
		ErrorModel errorModel = new ErrorModel(ErrorCode.UNAUTHORIZED, paramInfo.getParamEnMessage(), paramInfo.getParamLocalMessage());
		
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorModel));
    }
    
    private ParamInfoData getErrorMessage(ErrorCode code) {
		return this.paramService.getErrorMessage(code)
				.orElse(ParamInfoData.builder()
						.paramEnMessage("System error")
						.paramLocalMessage("System error")
						.build());
	}
    
}
