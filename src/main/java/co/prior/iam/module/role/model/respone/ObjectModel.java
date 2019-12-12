package co.prior.iam.module.role.model.respone;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
public class ObjectModel {

    private long objectId;
    private String objectCode;
    private String objectName;
    private long objectType;
    private Long objectParentId;
    private String objectUrl;
    private Float sorting ;
    
    @Builder.Default
    private List<ObjectModel> objects = new ArrayList<>();
    
}
