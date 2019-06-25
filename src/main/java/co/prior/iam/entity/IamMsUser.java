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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "iam_ms_user")
public class IamMsUser extends BaseEntity<IamMsUser> {

	@Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "iam_ms_user_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
	private Long userId;
	private String userCode;
	private String localFirstName;
	private String localMiddleName;
	private String localLastName;
	private String engFirstName;
	private String engMiddleName;
	private String engLastName;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userPassword;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String firstTimeLogin;
	
	private String isIamAdmin;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer noOfFailTimes;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String disableFlag;
	
	@JsonIgnore
	@OneToMany(mappedBy = "iamMsUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<IamMsUserRole> iamMsUserRoleSet;
	
}
