package co.prior.iam.module.object.model.respone;


import co.prior.iam.entity.IamMsObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectRespone {

    private long systemId;
    private long objectId;
    private String objectCode;
    private long objectParentId;
    private long objectTotalChild;
}
