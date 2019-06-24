package co.prior.iam.module.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.module.auth.model.request.ActivateUserRequest;
import co.prior.iam.module.auth.model.request.SignUpRequest;
import co.prior.iam.repository.IamMsUserRepository;
import co.prior.iam.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
    private final IamMsUserRepository iamMsUserRepository;
    
    public AuthService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, 
    		JwtTokenProvider jwtTokenProvider, IamMsUserRepository iamMsUserRepository) {
    	
    	this.authenticationManager = authenticationManager;
    	this.passwordEncoder = passwordEncoder;
    	this.jwtTokenProvider = jwtTokenProvider;
    	this.iamMsUserRepository = iamMsUserRepository;
    }
    
    @Transactional(noRollbackFor = Exception.class)
    public String signIn(String userCode, String password) throws Exception {
    	log.info("Service signIn userCode: {}", userCode);
    	
    	try {
	    	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userCode, password));
	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        this.iamMsUserRepository.findByUserCodeAndIsDeleted(userCode, "N").ifPresent(user -> {
	        	user.setNoOfFailTimes(0);
	        	this.iamMsUserRepository.save(user);
	        });
	        
	        return this.jwtTokenProvider.generateToken(authentication);
	        
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
    	log.info("Service signUp userCode: {}", request.getUserCode());
    	
    	if(this.iamMsUserRepository.existsByUserCodeAndDisableFlagAndIsDeleted(request.getUserCode(), "N", "N")) {
            throw new Exception("user code is already exist");
        }

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
        		.build();
        
        iamMsUser.setUserPassword(passwordEncoder.encode(request.getPassword()));

        return this.iamMsUserRepository.save(iamMsUser);
    }
    
    @Transactional
    public void activateUser(ActivateUserRequest request) throws Exception {
    	log.info("Service activateUser userCode: {}", request.getUserCode());
    	
    	IamMsUser iamMsUser = this.iamMsUserRepository.findByUserCodeAndUserPasswordAndIsDeleted(
    			request.getUserCode(), passwordEncoder.encode(request.getOldPassword()), "N")
    			.orElseThrow(() -> new Exception("password incorrect"));
    	
    	iamMsUser.setUserPassword(passwordEncoder.encode(request.getNewPassword()));
    	this.iamMsUserRepository.save(iamMsUser);
    }
    
}
