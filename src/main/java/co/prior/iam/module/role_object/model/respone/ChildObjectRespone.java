package co.prior.iam.module.role_object.model.respone;

import co.prior.iam.entity.IamMsSystem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildObjectRespone {
    private List<IamMsSystem> childObject;
}
