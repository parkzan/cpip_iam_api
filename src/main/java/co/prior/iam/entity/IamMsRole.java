package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;



import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


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
    private IamMsSystem iamMsSystem;


    @OneToMany(mappedBy = "iamMsRole",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsRoleObject> iamMsRoleObjects ;


    @JsonIgnore
	@OneToMany(mappedBy = "iamMsRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<IamMsUserRole> iamMsUserRoleSet;


}
