package co.prior.iam.module.auth.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.module.auth.model.request.ActivateUserRequest;
import co.prior.iam.module.auth.model.request.ChangePasswordRequest;
import co.prior.iam.module.auth.model.request.RefreshTokenRequest;
import co.prior.iam.module.auth.model.request.SignInRequest;
import co.prior.iam.module.auth.model.request.SignUpRequest;
import co.prior.iam.module.auth.model.response.AuthResponse;
import co.prior.iam.module.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
    
	@PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody SignInRequest request) {
		log.info("Controller signIn userCode: {}", request.getUserCode());
		
		AuthResponse response = this.authService.signIn(request.getUserCode(), request.getPassword());
		
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {
    	log.info("Controller signUp systemId: {}, userCode: {}", request.getSystemId(), request.getUserCode());
    	
    	IamMsUser iamMsUser = this.authService.signUp(request);
    	
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/auth/users/{user_id}")
                .buildAndExpand(iamMsUser.getUserId()).toUri();

        return ResponseEntity.created(location).build();
    }
    
    @PostMapping("/activate")
    public ResponseEntity<Void> activateUser(@Valid @RequestBody ActivateUserRequest request) {
    	log.info("Controller activateUser systemId: {}, userCode: {}", request.getSystemId(), request.getUserCode());
    	
    	this.authService.activateUser(request);
    	
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/password/new")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
    	log.info("Controller changePassword userId: {}", request.getUserId());
    	
    	this.authService.changePassword(request);
    	
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
    	log.info("Controller refreshToken token: {}", request.getRefreshToken());
    	
    	AuthResponse response = this.authService.refreshToken(request.getRefreshToken());
    	
    	return ResponseEntity.ok(response);
    }
    
}
