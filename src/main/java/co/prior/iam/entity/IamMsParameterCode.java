package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "iam_ms_parameter_code")
public class IamMsParameterCode  extends BaseEntity<IamMsParameterCode>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paramCodeId;
    private String paramCode;
    private String paramEnDescription;
    private String paramLocalDescription;

    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "param_group_id")
    private IamMsParameterGroup paramGroup;

}
