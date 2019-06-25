package co.prior.iam.module.user.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.entity.IamMsUser;
import co.prior.iam.model.PageableRequest;
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
    public ResponseEntity<Page<IamMsUser>> getUsers(@RequestBody PageableRequest request) {
		log.info("Controller getUsers page: {}, size: {}", request.getPage(), request.getSize());
		
		Page<IamMsUser> users = this.userService.getUsers(request);
		
        return ResponseEntity.ok(users);
    }
	
	@GetMapping("/user/{user_id}")
    @PreAuthorize("hasRole('ROLE_IAM_ADMIN')")
    public ResponseEntity<IamMsUser> getUser(@PathVariable(value = "user_id") long userId) throws Exception {
		log.info("Controller getUser userId: {}", userId);
		
		IamMsUser user = this.userService.getUser(userId);
		
        return ResponseEntity.ok(user);
    }
	
}
