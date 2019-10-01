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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "iam_ms_user")
public class IamMsUser extends BaseEntity<IamMsUser> {

	@Id
    @SequenceGenerator(name = "user_id_seq", sequenceName = "iam_ms_user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
	private Long userId;
	private String userCode;
	private String localFirstName;
	private String localMiddleName;
	private String localLastName;
	private String engFirstName;
	private String engMiddleName;
	private String engLastName;
	private String userPassword;
	private String firstTimeLogin;
	private String isIamAdmin;
	private Integer noOfFailTimes;
	private String disableFlag;
	
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "iamMsUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<IamMsUserRole> iamMsUserRoleSet;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "PROVINCE_ID")
	private PpiMsProvince province;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "SURVEY_ID")
	private PpiMsSurvey survey;

//	@ToString.Exclude
//	@ManyToOne
//	@JoinColumn(name = "PARAM_INFO_ID" )
//	@NotNull
//	private IamMsParameterInfo userType;

	@NotNull
	private Long userType;
//
//	@ToString.Exclude
//	@ManyToOne
//	@JoinColumn(name = "system_id")
//    private IamMsSystem iamMsSystem;
	
}
