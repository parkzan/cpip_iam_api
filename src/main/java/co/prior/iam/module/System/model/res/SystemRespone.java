package co.prior.iam.module.System.model.res;


import co.prior.iam.common.BaseApiRespone;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemRespone{

    private String systemCode;


}
