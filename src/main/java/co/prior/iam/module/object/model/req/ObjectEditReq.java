package co.prior.iam.module.object.model.req;


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
