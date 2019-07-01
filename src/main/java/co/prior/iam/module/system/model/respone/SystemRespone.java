package co.prior.iam.module.system.model.respone;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SystemRespone{

    private long systemId;
    private String systemCode;
    private String systemName;
    private String systemIcon;

}
