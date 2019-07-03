package co.prior.iam.module.object.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectDeleteReq {

    private long objectId;
    private long systemId;

}
