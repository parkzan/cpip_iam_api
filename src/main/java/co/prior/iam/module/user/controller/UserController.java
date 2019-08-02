package co.prior.iam.module.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.module.user.model.request.DeleteUserRequest;
import co.prior.iam.module.user.model.request.EditUserRequest;
import co.prior.iam.module.user.model.request.GetUsersRequest;
import co.prior.iam.module.user.model.response.GetUserResponse;
import co.prior.iam.module.user.model.response.IamMsUserPage;
import co.prior.iam.module.user.service.UserService;
import co.prior.iam.security.CurrentUser;
import co.prior.iam.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user/me")
    public ResponseEntity<UserPrincipal> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		log.info("Controller getCurrentUser userCode: {}", currentUser.getUserCode());
		
        return ResponseEntity.ok(currentUser);
    }
	
	@PostMapping("/users")
    @PreAuthorize("hasRole('ROLE_IAM_ADMIN')")
    public ResponseEntity<IamMsUserPage> getUsers(@RequestBody GetUsersRequest request) {
		log.info("Controller getUsers systemId: {}", request);
		
		IamMsUserPage response = this.userService.getUsers(request);
		
        return ResponseEntity.ok(response);
    }
	
	@GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('ROLE_IAM_ADMIN')")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable(value = "user_id") long userId) {
		log.info("Controller getUser userId: {}", userId);
		
		GetUserResponse response = this.userService.getUser(userId);
		
        return ResponseEntity.ok(response);
    }
	
	@PutMapping("/user")
	@PreAuthorize("hasRole('ROLE_IAM_ADMIN')")
	public ResponseEntity<Void> editUser(@Valid @RequestBody EditUserRequest request) {
		log.info("Controller editUser userId: {}", request.getUserId());
		
		this.userService.editUser(request);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/user")
	@PreAuthorize("hasRole('ROLE_IAM_ADMIN')")
	public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequest request) {
		log.info("Controller deleteUser userId: {}", request.getUserId());
		
		this.userService.deleteUser(request.getUserId());
		
		return ResponseEntity.noContent().build();
	}
	
}
