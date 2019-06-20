package co.prior.iam.module.user.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.module.user.service.UserService;
import co.prior.iam.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user/me")
    public ResponseEntity<UserPrincipal> getCurrentUser(@AuthenticationPrincipal UserPrincipal currentUser) {
		log.info("Controller getCurrentUser userCode: {}", currentUser.getUserCode());
		
        return ResponseEntity.ok(currentUser);
    }
	
	@GetMapping("/users")
    @PreAuthorize("hasRole('IAM_ADMIN')")
    public ResponseEntity<Collection<IamMsUser>> getUsers() {
		log.info("Controller getUsers");
		
		Collection<IamMsUser> users = this.userService.getUsers();
		
        return ResponseEntity.ok(users);
    }
	
	@GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('IAM_ADMIN')")
    public ResponseEntity<IamMsUser> getUser(@PathVariable(value = "user_id") long userId) throws Exception {
		log.info("Controller getUsers");
		
		IamMsUser user = this.userService.getUser(userId);
		
        return ResponseEntity.ok(user);
    }
	
}
