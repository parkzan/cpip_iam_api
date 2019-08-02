package co.prior.iam.module.user.controller;

import java.net.URI;
import java.util.List;

import co.prior.iam.module.user.model.response.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.prior.iam.entity.IamMsUserRole;
import co.prior.iam.module.user.model.request.CreateUserRoleRequest;
import co.prior.iam.module.user.model.request.DeleteUserRoleRequest;
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
    public ResponseEntity<GetUserRolesResponse> getUserRoles(@PathVariable(value = "user_id") long userId) {
		log.info("Controller getUserRoles userId: {}", userId);
		
		GetUserRolesResponse response = this.userRoleService.getUserRoles(userId);
		
        return ResponseEntity.ok(response);
    }

	@GetMapping("/user/system/{system_id}")
	public ResponseEntity<List<GetUserRoleSystemRespone>> getUserRolesBySystem(@PathVariable(value = "system_id") long systemId) {
		log.info("Controller getUserRoles systemId: {}", systemId);



		return ResponseEntity.ok(this.userRoleService.getUserRolesBySystem(systemId));
	}


	@GetMapping("/user/{user_id}/roles/system")
	public ResponseEntity<List<UserRoleSystemRespone> > getAllUserRoles(@PathVariable(value = "user_id") long userId) {
		log.info("Controller getUserRoles userId: {}", userId);

		List<UserRoleSystemRespone>  response = this.userRoleService.getAllUserRoles(userId);

		return ResponseEntity.ok(response);
	}


	
	@GetMapping("/role/{role_id}/users")
    public ResponseEntity<GetRoleUsersResponse> getRoleUsers(@PathVariable(value = "role_id") long roleId) {
		log.info("Controller getRoleUsers roleId: {}", roleId);
		
		GetRoleUsersResponse response = this.userRoleService.getRoleUsers(roleId);
		
        return ResponseEntity.ok(response);
    }
	
	@PostMapping("/user/role")
	public ResponseEntity<Void> createUserRole(@RequestBody CreateUserRoleRequest request) {
		log.info("Controller createUserRole userId: {}", request.getUserId());
		
		IamMsUserRole iamMsUserRole = this.userRoleService.createUserRole(request);
    	
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/user/{user_id}/roles")
                .buildAndExpand(iamMsUserRole.getUserRoleId()).toUri();

        return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/user/role")
	public ResponseEntity<Void> deleteUserRole(@RequestBody DeleteUserRoleRequest request) {
		log.info("Controller deleteUserRole userRoleId: {}", request.getUserRoleId());
		
		this.userRoleService.deleteUserRole(request.getUserRoleId());
		
		return ResponseEntity.noContent().build();
	}
	
}
