package co.prior.iam.module.role.model.respone;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectModel {

    private long objectId;
    private String objectCode;
    private String objectName;
    
    @Builder.Default
    private List<ObjectModel> objects = new ArrayList<>();
    
}
