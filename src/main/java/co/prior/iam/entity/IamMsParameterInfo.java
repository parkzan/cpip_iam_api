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
public class IamMsParameterInfo extends BaseEntity<IamMsParameterInfo>{

    @Id
    @SequenceGenerator(name = "param_info_id_seq", sequenceName = "iam_ms_parameter_info_param_info_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "param_info_id_seq")
    private Long paramInfoId;
    private String paramInfo;
    private String paramEnDescription;
    private String paramLocalDescription;

    @ToString.Exclude
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "param_group_id")
    private IamMsParameterGroup paramGroup;

}
