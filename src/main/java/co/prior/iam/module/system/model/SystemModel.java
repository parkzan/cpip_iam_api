package co.prior.iam.module.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemModel {

    private String systemCode;
    private String systemName;
    private String systemIcon;
}
