package co.prior.iam.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Entity
@Table (name = "iam_ms_role")
public class IamMsRole extends BaseEntity<IamMsRole> {

    @Id
    @SequenceGenerator(name = "role_id_seq", sequenceName = "iam_ms_role_role_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Long roleId;
    private String roleCode;
    private String roleName;
    private String roleIcon;

    @ManyToOne
    @JoinColumn(name = "system_id")
    private IamMsSystem iamMsSystem;

    @JsonIgnore
    @OneToMany(mappedBy = "iamMsRole",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<IamMsRoleObject> iamMsRoleObjects ;

    @JsonIgnore
	@OneToMany(mappedBy = "iamMsRole", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<IamMsUserRole> iamMsUserRoleSet;

}
