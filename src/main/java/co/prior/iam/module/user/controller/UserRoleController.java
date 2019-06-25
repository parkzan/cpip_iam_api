package co.prior.iam.module.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.prior.iam.module.user.model.request.DeleteUserRoleRequest;
import co.prior.iam.module.user.model.response.GetUserRolesResponse;
import co.prior.iam.module.user.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = "/api")
@PreAuthorize("hasRole('ROLE_IAM_ADMIN')")
public class UserRoleController {

	private final UserRoleService userRoleService;
	
	public UserRoleController(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	@GetMapping("/user/{user_id}/roles")
    public ResponseEntity<GetUserRolesResponse> getUserRoles(@PathVariable(value = "user_id") long userId) throws Exception {
		log.info("Controller getUserRoles userId: {}", userId);
		
		GetUserRolesResponse response = this.userRoleService.getUserRoles(userId);
		
        return ResponseEntity.ok(response);
    }
	
	@DeleteMapping("/user/role")
	public ResponseEntity<Void> deleteUserRole(@RequestBody DeleteUserRoleRequest request) throws Exception {
		log.info("Controller deleteUserRole userRoleId: {}", request.getUserRoleId());
		
		this.userRoleService.deleteUserRole(request.getUserRoleId());
		
		return ResponseEntity.noContent().build();
	}
	
}
