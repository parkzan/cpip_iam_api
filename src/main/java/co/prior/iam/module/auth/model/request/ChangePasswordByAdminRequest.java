package co.prior.iam.module.auth.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordByAdminRequest {

    private long userId;

    @NotBlank
    private String newPassword;
}
