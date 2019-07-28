package co.prior.iam.module.system.model.respone;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SystemRespone{

    private long systemId;
    private String systemCode;
    private String systemName;
    private String systemIcon;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;

}
