package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@EqualsAndHashCode(callSuper = false)
@Entity
@Table (name = "iam_ms_role")
@Getter
@Setter
public class IamMsRole extends BaseEntity {


    @Id
    @SequenceGenerator(name = "role_id_seq", sequenceName = "iam_ms_role_role_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Long roleId;

    private String roleCode;

    private String roleName;

    private String roleIcon;

    @ManyToOne
    @JoinColumn(name = "system_id")
    @JsonIgnore
    private IamMsSystem.IamMsSystem iamMsSystem;

    @OneToMany(mappedBy = "iamMsRole",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsRoleObject> iamMsRoleObjects ;

}
