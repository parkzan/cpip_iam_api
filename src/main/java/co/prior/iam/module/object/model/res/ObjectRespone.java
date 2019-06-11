package co.prior.iam.module.object.model.res;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.apache.bcel.classfile.Code;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectRespone {

    private String message;
    private String Code ;
}
