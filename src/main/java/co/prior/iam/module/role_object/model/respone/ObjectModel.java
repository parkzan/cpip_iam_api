package co.prior.iam.module.role_object.model.respone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectModel {

    private long objectId;
    private String objectName;
    private List<ObjectModel> objects = new ArrayList<>();
}
