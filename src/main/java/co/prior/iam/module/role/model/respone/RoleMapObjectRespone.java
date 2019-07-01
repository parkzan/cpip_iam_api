package co.prior.iam.module.role.model.respone;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleMapObjectRespone {

   private long systemId;
   private long roleId;
   private String roleName;
   
   @Builder.Default
   private List<ObjectModel> objects = new ArrayList<>();

}
