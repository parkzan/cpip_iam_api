package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "iam_ms_system")
@Getter
@Setter
public class IamMsSystem extends BaseEntity{
    @Id
    @SequenceGenerator(name = "system_id_seq", sequenceName = "iam_ms_system_system_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_id_seq")
    private Long systemId;


    private String systemCode;


    private String systemName;


    private String systemIcon;


    @OneToMany(mappedBy = "iamMsSystem",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsRole> iamMsRoles ;

    @OneToMany(mappedBy = "iamMsSystem",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsObject> iamMsObjects ;

    @OneToMany(mappedBy = "iamMsSystem",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsRoleObject> iamMsRoleObjects ;


}
