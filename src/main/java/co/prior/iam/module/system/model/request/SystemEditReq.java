package co.prior.iam.module.system.model.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemEditReq {
    private String systemCode;
    private String newName;
    private String newIcon;
}
