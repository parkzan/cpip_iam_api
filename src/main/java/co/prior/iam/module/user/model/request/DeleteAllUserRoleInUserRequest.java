package co.prior.iam.module.user.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteAllUserRoleInUserRequest {
    private long userId;
    private long systemId;
}
