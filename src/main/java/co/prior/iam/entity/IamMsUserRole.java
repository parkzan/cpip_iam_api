package co.prior.iam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "iam_ms_user_role")
public class IamMsUserRole extends BaseEntity<IamMsUserRole> {
	
	@Id
    @SequenceGenerator(name = "user_role_id_seq", sequenceName = "iam_ms_user_role_user_role_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_seq")
	private Long userRoleId;
	
	@ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
	private IamMsUser iamMsUser;
	
	@ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "system_id")
	private IamMsSystem iamMsSystem;
	
	@ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "role_id")
	private IamMsRole iamMsRole;
	
}
