package co.prior.iam.module.object.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectEditReq {
	
    private Long systemId;
    private Long objectId;
    private String newName;
    private String url;
    private long type;

}
