package co.prior.iam.module.object.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectCreateReq {

    private String objectCode;
    private String objectName;
    private Long systemId;
    private Long objectParentId;

}
