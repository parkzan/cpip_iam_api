package co.prior.iam.module.role.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleMapObjectReq {

    private long systemId;
    private long roleId;
    private List<Long> newObjectId;

}
