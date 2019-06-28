package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "iam_ms_parameter_group")
public class IamMsParameterGroup extends BaseEntity<IamMsParameterGroup> {

    @Id
    @SequenceGenerator(name = "param_group_id_seq", sequenceName = "iam_ms_parameter_group_param_group_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "param_group_id_seq")
    private Long paramGroupId;
    private String paramGroup;
    private String paramEnDescription;
    private String paramLocalDescription;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "paramGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<IamMsParameterInfo> paramInfoSet;

}
