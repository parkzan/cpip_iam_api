package co.prior.iam.module.object.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectEditReq {
    private Long ObjectId;
    private String newName;


}
