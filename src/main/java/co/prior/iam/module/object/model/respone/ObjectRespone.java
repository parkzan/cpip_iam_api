package co.prior.iam.module.object.model.respone;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectRespone {

    private String message;
    private String Code ;
}
