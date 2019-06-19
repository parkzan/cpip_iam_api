package co.prior.iam.module.system.model.respone;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemRespone{

    private Long systemId;
    private String systemCode;
    private String systemName;
    private String systemIcon;


}
