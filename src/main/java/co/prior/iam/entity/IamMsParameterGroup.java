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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paramGroupId;
    private String paramGroup;
    private String paramEnDescription;
    private String paramLocalDescription;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "paramGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<IamMsParameterCode> paramCode;

}
