package co.prior.iam.module.param.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParamInfoData {
    long paramInfoId;
    String paramInfo;
    String paramEnMessage;
    String paramLocalMessage;
    
}
