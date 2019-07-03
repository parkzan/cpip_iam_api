package co.prior.iam.module.role.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetRoleObjectReq {

    private long systemId;
    private long roleId;

}
