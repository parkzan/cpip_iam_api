package co.prior.iam.entity;


import lombok.Data;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Data
@Entity
@Immutable
@Table(name = "V_PARAMETER_DATA")
public class VParameterData implements Serializable {

    private Long paramGroupId;
    private String paramGroup;
    private String paramGroupEnDescription;
    private String paramGroupLocalDescription;
    private String paramGroupIsDeleted;

    @Id
    private Long paramInfoId;
    private String paramCode;
    private String paramInfoEnDescription;
    private String paramInfoLocalDescription;
    private String paramInfoIsDeleted;




}
