package co.prior.iam.module.object.model.respone;

import co.prior.iam.entity.IamMsParameterInfo;
import co.prior.iam.repository.ParamInfoRepository;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectRespone {

    private long systemId;
    private long objectId;
    private String objectCode;
    private String objectName;
    private long objectParentId;
    private long objectTotalChild;
    private String objectUrl;
    private IamMsParameterInfo type;
    
}
