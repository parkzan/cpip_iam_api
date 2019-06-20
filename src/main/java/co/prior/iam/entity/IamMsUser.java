package co.prior.iam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
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
	private String userPassword;
	private String firstTimeLogin;
	private String isIamAdmin;
	private Integer noOfFailTimes;
	private String disableFlag;
	
}
