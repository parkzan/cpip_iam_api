package co.prior.iam.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseApiRespone <T> {

    private  String apiCode;
    private String messageTH;
    private String mesageEn;
    private String resCode;
    private T result;


}


