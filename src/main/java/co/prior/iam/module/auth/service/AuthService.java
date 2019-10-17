package co.prior.iam.module.auth.service;

import java.time.LocalDateTime;
import java.util.Calendar;

import co.prior.iam.entity.*;
import co.prior.iam.model.SystemConfig;
import co.prior.iam.model.UserType;
import co.prior.iam.module.auth.model.request.*;
import co.prior.iam.module.param.model.response.ParamInfoData;
import co.prior.iam.module.param.service.ParamService;
import co.prior.iam.repository.*;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import co.prior.iam.error.exception.BadRequestException;
import co.prior.iam.error.exception.DataDuplicateException;
import co.prior.iam.error.exception.DataNotFoundException;
import co.prior.iam.error.exception.UnauthorizedException;
import co.prior.iam.model.AnswerFlag;
import co.prior.iam.model.ErrorCode;
import co.prior.iam.module.auth.model.response.AuthResponse;
import co.prior.iam.security.CustomDetailsService;
import co.prior.iam.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	
	@Value("${security.jwt.access.secret}")
    private String accessSecret;

    @Value("${security.jwt.access.expirationTime}")
    private int accessExpirationTime;
    
    @Value("${security.jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${security.jwt.refresh.expirationTime}")
    private int refreshExpirationTime;
    
    private final CustomDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
    private final IamMsUserRepository iamMsUserRepository;
    private final ParamInfoRepository paramInfoRepository;
    private final PpiMsProvinceRepository ppiMsProvinceRepository;
    private final PpiMsSurveyRepository ppiMsSurveyRepository;
	private final ParamService paramService;
    
    public AuthService(CustomDetailsService userDetailsService, AuthenticationManager authenticationManager, 
    		PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, IamMsUserRepository iamMsUserRepository, ParamInfoRepository paramInfoRepository , PpiMsProvinceRepository ppiMsProvinceRepository
	,PpiMsSurveyRepository ppiMsSurveyRepository , ParamService paramService) {
    	
    	this.userDetailsService = userDetailsService;
    	this.authenticationManager = authenticationManager;
    	this.passwordEncoder = passwordEncoder;
    	this.jwtTokenProvider = jwtTokenProvider;
    	this.iamMsUserRepository = iamMsUserRepository;
    	this.paramInfoRepository=paramInfoRepository;
    	this.ppiMsProvinceRepository = ppiMsProvinceRepository;
    	this.ppiMsSurveyRepository = ppiMsSurveyRepository;
    	this.paramService = paramService;
    }


    
    @Transactional(noRollbackFor = UnauthorizedException.class)
    public AuthResponse signIn(String userCode, String password, String isIamAdmin) {
    	log.info("Service signIn userCode: {}, isIamAdmin: {}, password: {}", userCode, isIamAdmin,password);

    	ParamInfoData noFailTime = getSystemConfig(SystemConfig.NO_OF_FAIL_TIME);

		try{
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCode, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			IamMsUser iamMsUser = this.iamMsUserRepository.findByUserCodeAndIsIamAdminAndIsDeleted(
					userCode, isIamAdmin, AnswerFlag.N.toString())
					.orElseThrow(() -> new UnauthorizedException(ErrorCode.IS_NOT_ADMIN));
			log.debug("iamMsUser.getUserPassword(): {}", iamMsUser.getUserPassword());
			log.debug("passwordEncoder.encode(password): {}", passwordEncoder.encode(password));

			if (iamMsUser.getDisableFlag().equals(AnswerFlag.Y.toString())) {
				throw new UnauthorizedException(ErrorCode.USER_DISABLED);
			}
//			if(iamMsUser.getFirstTimeLogin().equals(AnswerFlag.N.toString())){
//
//			}
			if (!passwordEncoder.matches(password, iamMsUser.getUserPassword())) {
				if (iamMsUser.getNoOfFailTimes() <= Integer.parseInt(noFailTime.getParamEnMessage())) {
					int failedAttempt = iamMsUser.getNoOfFailTimes() + 1;
					if (failedAttempt == Integer.parseInt(noFailTime.getParamEnMessage())) {
						iamMsUser.setDisableFlag(AnswerFlag.Y.toString());
						iamMsUser.setNoOfFailTimes(failedAttempt);
						this.iamMsUserRepository.save(iamMsUser);
					} else {
						iamMsUser.setNoOfFailTimes(failedAttempt);
						this.iamMsUserRepository.save(iamMsUser);
					}
				}


				throw new UnauthorizedException(ErrorCode.PASSWORD_INCORRECT);
			}
//			if(AnswerFlag.Y.toString().equalsIgnoreCase(iamMsUser.getFirstTimeLogin())){
//				throw new UnauthorizedException(ErrorCode.USER_DISABLED);
//			}

			iamMsUser.setNoOfFailTimes(0);
			iamMsUser.setLastLoginTime(LocalDateTime.now());

			this.iamMsUserRepository.save(iamMsUser);

			return this.generateAuthResponse(authentication);


    	} catch (BadCredentialsException e) {
    		this.iamMsUserRepository.findByUserCodeAndIsIamAdminAndIsDeleted(
    				userCode, isIamAdmin, AnswerFlag.N.toString()).ifPresent(user -> {
    			int failedAttempt = user.getNoOfFailTimes() + 1;
				if(failedAttempt == Integer.parseInt(noFailTime.getParamEnMessage())){
					user.setDisableFlag(AnswerFlag.Y.toString());
					user.setNoOfFailTimes(failedAttempt);
					this.iamMsUserRepository.save(user);
				}
				else {
					user.setNoOfFailTimes(failedAttempt);
					this.iamMsUserRepository.save(user);
				}
	        });

        	throw new UnauthorizedException(ErrorCode.PASSWORD_INCORRECT);

        }  catch (LockedException e) {
        	throw new UnauthorizedException(ErrorCode.USER_DISABLED);
        }catch (DisabledException e) {
			throw new UnauthorizedException(ErrorCode.USER_DISABLED);
		}
    }
    
    @Transactional()
    public IamMsUser signUp(SignUpRequest request) {
    	log.info("Service signUp systemId: {}, userCode: {}",request.getUserCode());

    	ParamInfoData userTypeAdmin = getUserType(UserType.ADMIN);
    	if(this.iamMsUserRepository.existsByUserCodeAndIsDeleted(
    			request.getUserCode(), AnswerFlag.N.toString())) {
    		
            throw new DataDuplicateException(ErrorCode.USER_DUPLICATED);
        }


		IamMsParameterInfo userType = this.paramInfoRepository.findByParamInfoIdAndIsDeleted(request.getUserType(),AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.INTERNAL_SERVER_ERROR));

    	if(request.getProvinceId() != null){

    	PpiMsProvince ppiMsProvince = this.ppiMsProvinceRepository.findByProvinceIdAndIsDeleted(request.getProvinceId(),AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.PROVINCE_NOT_FOUND));


    			
    	String isIamAdmin = request.getIsIamAdmin().toString();
        IamMsUser iamMsUser = IamMsUser.builder()
        		.userCode(request.getUserCode())
        		.localFirstName(request.getLocalFirstName())
        		.localMiddleName(request.getLocalMiddleName())
        		.localLastName(request.getLocalLastName())
        		.engFirstName(request.getEngFirstName())
        		.engMiddleName(request.getEngMiddleName())
        		.engLastName(request.getEngLastName())
        		.firstTimeLogin(AnswerFlag.Y.toString().equalsIgnoreCase(isIamAdmin)? AnswerFlag.N.toString() : AnswerFlag.Y.toString())
        		.isIamAdmin(isIamAdmin)
        		.noOfFailTimes(0)
        		.disableFlag(AnswerFlag.Y.toString())
				.userType(userType.getParamInfoId())
				.province(ppiMsProvince)
        		.build();

        iamMsUser.setUpdatedBy(null);
        iamMsUser.setUpdatedDate(null);
        iamMsUser.setUserPassword(passwordEncoder.encode(request.getPassword()));

        return this.iamMsUserRepository.save(iamMsUser);
    	}else if (request.getSurveyId() != null){
			PpiMsSurvey ppiMsSurvey = this.ppiMsSurveyRepository.findBySurveyIdAndIsDeleted(request.getSurveyId(),AnswerFlag.N.toString())
					.orElseThrow(() -> new DataNotFoundException(ErrorCode.SURVEY_NOT_FOUND));



			String isIamAdmin = request.getIsIamAdmin().toString();
			IamMsUser iamMsUser = IamMsUser.builder()
					.userCode(request.getUserCode())
					.localFirstName(request.getLocalFirstName())
					.localMiddleName(request.getLocalMiddleName())
					.localLastName(request.getLocalLastName())
					.engFirstName(request.getEngFirstName())
					.engMiddleName(request.getEngMiddleName())
					.engLastName(request.getEngLastName())
					.firstTimeLogin(AnswerFlag.Y.toString().equalsIgnoreCase(isIamAdmin)? AnswerFlag.N.toString() : AnswerFlag.Y.toString())
					.isIamAdmin(isIamAdmin)
					.noOfFailTimes(0)
					.disableFlag(AnswerFlag.Y.toString())
					.userType(userType.getParamInfoId())
					.survey(ppiMsSurvey)
					.build();

			iamMsUser.setUserPassword(passwordEncoder.encode(request.getPassword()));


			return this.iamMsUserRepository.save(iamMsUser);
		}
		String isIamAdmin = request.getIsIamAdmin().toString();
		IamMsUser iamMsUser = IamMsUser.builder()
				.userCode(request.getUserCode())
				.localFirstName(request.getLocalFirstName())
				.localMiddleName(request.getLocalMiddleName())
				.localLastName(request.getLocalLastName())
				.engFirstName(request.getEngFirstName())
				.engMiddleName(request.getEngMiddleName())
				.engLastName(request.getEngLastName())
				.firstTimeLogin(AnswerFlag.Y.toString().equalsIgnoreCase(isIamAdmin)? AnswerFlag.N.toString() : AnswerFlag.Y.toString())
				.isIamAdmin(isIamAdmin)
				.noOfFailTimes(0)
				.build();

		if(request.getIsIamAdmin().equals(AnswerFlag.Y.toString())){
			iamMsUser.setUserType(userTypeAdmin.getParamInfoId());
			iamMsUser.setDisableFlag(AnswerFlag.N.toString());
		}
		else{
			iamMsUser.setUserType(userType.getParamInfoId());
			iamMsUser.setDisableFlag(AnswerFlag.Y.toString());
		}


		iamMsUser.setUserPassword(passwordEncoder.encode(request.getPassword()));



		return this.iamMsUserRepository.save(iamMsUser);
    }
    
    @Transactional
    public void activateUser(ActivateUserRequest request) {
    	log.info("Service activateUser userCode: {}", request.getUserCode());
    	
    	IamMsUser iamMsUser = this.iamMsUserRepository.findByUserCodeAndIsDeleted(
    			request.getUserCode(), AnswerFlag.N.toString())
    			.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
    	
    	if (!passwordEncoder.matches(request.getOldPassword(), iamMsUser.getUserPassword())) {
    		throw new BadRequestException(ErrorCode.PASSWORD_INCORRECT);
    	}
    	
    	iamMsUser.setUserPassword(passwordEncoder.encode(request.getNewPassword()));
    	iamMsUser.setFirstTimeLogin(AnswerFlag.N.toString());
		iamMsUser.setFirstTimeLogin(AnswerFlag.N.toString());
    	this.iamMsUserRepository.save(iamMsUser);
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
    	log.info("Service changePassword userId: {}", request.getUserId());
    	
    	IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(request.getUserId(), AnswerFlag.N.toString())
    			.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
    	
    	if (!passwordEncoder.matches(request.getOldPassword(), iamMsUser.getUserPassword())) {
    		throw new BadRequestException(ErrorCode.PASSWORD_INCORRECT);
    	}
    	
    	iamMsUser.setUserPassword(passwordEncoder.encode(request.getNewPassword()));
    	iamMsUser.setNoOfFailTimes(0);
		iamMsUser.setDisableFlag(AnswerFlag.N.toString());
    	this.iamMsUserRepository.save(iamMsUser);
    }

	@Transactional
    public void changePasswordByAdmin(ChangePasswordByAdminRequest request){
		log.info("Service changePassword userId: {}", request.getUserId());
		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(request.getUserId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));
		iamMsUser.setUserPassword(passwordEncoder.encode(request.getNewPassword()));
		iamMsUser.setFirstTimeLogin(AnswerFlag.Y.toString());
		iamMsUser.setNoOfFailTimes(0);
		iamMsUser.setDisableFlag(AnswerFlag.N.toString());
		this.iamMsUserRepository.save(iamMsUser);
	}
    
    public AuthResponse refreshToken(String token) {
    	log.info("Service refreshToken token: {}", token);
    	
    	if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token, refreshSecret)) {
            long userId = jwtTokenProvider.getUserIdFromJWT(token, refreshSecret);
            UserDetails userDetails = userDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            		userDetails, null, null);
            
            return this.generateAuthResponse(authentication);
    	}
    	
    	throw new BadRequestException(ErrorCode.INVALID_REFRESH_TOKEN);
    }

	private AuthResponse generateAuthResponse(Authentication authentication) {
		Calendar accessExpiredDateTime = Calendar.getInstance();
		accessExpiredDateTime.add(Calendar.MINUTE, accessExpirationTime / 60);
		Calendar refreshExpiredDateTime = Calendar.getInstance();
		refreshExpiredDateTime.add(Calendar.MINUTE, refreshExpirationTime / 60);
		String accessToken = this.jwtTokenProvider.generateToken(authentication, accessSecret, accessExpirationTime);
		String refreshToken = this.jwtTokenProvider.generateToken(authentication, refreshSecret, refreshExpirationTime);
		
		return AuthResponse.builder()
				.accessToken(accessToken)
				.expiresIn(accessExpirationTime)
				.expiresAt(accessExpiredDateTime.getTimeInMillis() / 1000)
				.refreshToken(refreshToken)
				.refreshExpiresIn(refreshExpirationTime)
				.refreshExpiresAt(refreshExpiredDateTime.getTimeInMillis() / 1000)
				.build();
	}
	@Transactional
	public void resetWrongLoginUsers(ResetUserRequest request) {
		log.info("Service resetWrongLoginUsers userId: {}", request.getUserId());

		IamMsUser iamMsUser = this.iamMsUserRepository.findByUserIdAndIsDeleted(request.getUserId(), AnswerFlag.N.toString())
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND));

		iamMsUser.setNoOfFailTimes(0);
		this.iamMsUserRepository.save(iamMsUser);
	}

	private ParamInfoData getUserType(UserType type) {
		return this.paramService.getUserType(type)
				.orElse(ParamInfoData.builder()
						.paramEnMessage("System error")
						.paramLocalMessage("System error")
						.build());
	}

	private ParamInfoData getSystemConfig(SystemConfig value) {
		return this.paramService.getSystemConfig(value)
				.orElse(ParamInfoData.builder()
						.paramEnMessage("System error")
						.paramLocalMessage("System error")
						.build());
	}
}
