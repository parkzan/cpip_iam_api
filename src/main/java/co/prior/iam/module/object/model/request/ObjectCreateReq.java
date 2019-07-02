package co.prior.iam.module.object.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectCreateReq {

    private String objectCode;
    private String objectName;
    private long systemId;
    private long objectParentId;

}
