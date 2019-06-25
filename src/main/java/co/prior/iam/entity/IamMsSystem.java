package co.prior.iam.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "iam_ms_system")
@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
public class IamMsSystem extends BaseEntity<IamMsSystem> {

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

    @JsonIgnore
	@OneToMany(mappedBy = "iamMsSystem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<IamMsUserRole> iamMsUserRoleSet;
    
    @JsonIgnore
	@OneToMany(mappedBy = "iamMsSystem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<IamMsUser> iamMsUserSet;

}
