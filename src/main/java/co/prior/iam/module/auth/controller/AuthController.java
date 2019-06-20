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
import co.prior.iam.module.auth.model.request.SignInRequest;
import co.prior.iam.module.auth.model.request.SignUpRequest;
import co.prior.iam.module.auth.model.response.SignInResponse;
import co.prior.iam.module.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
    
	@PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
		log.info("Controller signIn userCode: {}", request.getUserCode());
		
        return ResponseEntity.ok(SignInResponse.builder()
        		.accessToken(this.authService.signIn(request.getUserCode(), request.getPassword()))
        		.build());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) throws Exception {
    	log.info("Controller signUp userCode: {}", request.getUserCode());
    	
    	IamMsUser iamMsUser = this.authService.signUp(request);
    	
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{user_id}")
                .buildAndExpand(iamMsUser.getUserId()).toUri();

        return ResponseEntity.created(location).build();
    }
    
}
