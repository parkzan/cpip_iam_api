package co.prior.iam.module.user.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateUserRoleRequest {

	@NotBlank
	private long userId;

	@NotBlank
	private long systemId;

	@NotBlank
	private long roleId;

}
