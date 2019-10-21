package co.prior.iam.module.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuObject {
    private String objectCode;
    private String objectName;
}
