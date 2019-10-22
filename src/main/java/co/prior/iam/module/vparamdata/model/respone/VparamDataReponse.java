package co.prior.iam.module.vparamdata.model.respone;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VparamDataReponse {

    private Long objectParentId;
    private long objectId;
    private String objectCode;
    private String objectName;
    private String objectUrl;
}
