package co.prior.iam.module.user.model.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRoleRequest {

	@NotBlank
	private long userId;

	@NotBlank
	private long systemId;

	@NotBlank
	private long roleId;

}
