package co.prior.iam.module.auth.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
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

import co.prior.iam.entity.IamMsSystem;
import co.prior.iam.entity.IamMsUser;
import co.prior.iam.module.auth.model.request.ActivateUserRequest;
import co.prior.iam.module.auth.model.request.SignUpRequest;
import co.prior.iam.module.auth.model.response.AuthResponse;
import co.prior.iam.repository.IamMsUserRepository;
import co.prior.iam.repository.SystemRepository;
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
	private final SystemRepository iamMsSystemRepository;
    private final IamMsUserRepository iamMsUserRepository;
    
    public AuthService(CustomDetailsService userDetailsService, AuthenticationManager authenticationManager, 
    		PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, 
    		SystemRepository iamMsSystemRepository, IamMsUserRepository iamMsUserRepository) {
    	
    	this.userDetailsService = userDetailsService;
    	this.authenticationManager = authenticationManager;
    	this.passwordEncoder = passwordEncoder;
    	this.jwtTokenProvider = jwtTokenProvider;
    	this.iamMsSystemRepository = iamMsSystemRepository;
    	this.iamMsUserRepository = iamMsUserRepository;
    }
    
    @Transactional(noRollbackFor = Exception.class)
    public AuthResponse signIn(String userCode, String password) throws Exception {
    	log.info("Service signIn userCode: {}", userCode);
    	
    	try {
	    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCode, password));
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        this.iamMsUserRepository.findByUserCodeAndIsDeleted(userCode, "N").ifPresent(user -> {
	        	user.setNoOfFailTimes(0);
	        	this.iamMsUserRepository.save(user);
	        });
	        
	        return this.generateAuthResponse(authentication);
	        
    	} catch (BadCredentialsException e) {
    		this.iamMsUserRepository.findByUserCodeAndIsDeleted(userCode, "N").ifPresent(user -> {
    			int failedAttempt = user.getNoOfFailTimes() + 1;
	        	user.setNoOfFailTimes(failedAttempt);
	        	this.iamMsUserRepository.save(user);
	        });
    		
        	throw new Exception("user code or password incorrect");
		
        } catch (DisabledException e) {
        	throw new Exception("user code is disabled");
        	
        } catch (LockedException e) {
        	throw new Exception("user code is locked");
        }
    }
    
    @Transactional
    public IamMsUser signUp(SignUpRequest request) throws Exception {
    	log.info("Service signUp systemId: {}, userCode: {}", request.getSystemId(), request.getUserCode());
    	
    	if(this.iamMsUserRepository.existsByIamMsSystem_SystemIdAndUserCodeAndIsDeleted(
    			request.getSystemId(), request.getUserCode(), "N")) {
    		
            throw new Exception("user code is already exist");
        }

    	IamMsSystem iamMsSystem = this.iamMsSystemRepository.findBySystemIdAndIsDeleted(request.getSystemId(), "N")
    			.orElseThrow(() -> new Exception("system not found"));
    			
    	String isIamAdmin = request.getIsIamAdmin();
        IamMsUser iamMsUser = IamMsUser.builder()
        		.userCode(request.getUserCode())
        		.localFirstName(request.getLocalFirstName())
        		.localMiddleName(request.getLocalMiddleName())
        		.localLastName(request.getLocalLastName())
        		.engFirstName(request.getEngFirstName())
        		.engMiddleName(request.getEngMiddleName())
        		.engLastName(request.getEngLastName())
        		.firstTimeLogin("Y".equalsIgnoreCase(isIamAdmin)? "N" : "Y")
        		.isIamAdmin(isIamAdmin)
        		.noOfFailTimes(0)
        		.disableFlag("N")
        		.iamMsSystem(iamMsSystem)
        		.build();
        
        iamMsUser.setUserPassword(passwordEncoder.encode(request.getPassword()));

        return this.iamMsUserRepository.save(iamMsUser);
    }
    
    @Transactional
    public void activateUser(ActivateUserRequest request) throws Exception {
    	log.info("Service activateUser userCode: {}", request.getUserCode());
    	
    	IamMsUser iamMsUser = this.iamMsUserRepository.findByIamMsSystem_SystemIdAndUserCodeAndUserPasswordAndIsDeleted(
    			request.getSystemId(), request.getUserCode(), passwordEncoder.encode(request.getOldPassword()), "N")
    			.orElseThrow(() -> new Exception("password incorrect"));
    	
    	iamMsUser.setUserPassword(passwordEncoder.encode(request.getNewPassword()));
    	this.iamMsUserRepository.save(iamMsUser);
    }
    
    public AuthResponse refreshToken(String token) throws Exception {
    	log.info("Service refreshToken token: {}", token);
    	
    	if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token, refreshSecret)) {
            Long userId = jwtTokenProvider.getUserIdFromJWT(token, refreshSecret);
            UserDetails userDetails = userDetailsService.loadUserById(userId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            		userDetails, null, null);
            
            return this.generateAuthResponse(authentication);
    	}
    	
    	throw new Exception("refresh token invalid");
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
    
}
