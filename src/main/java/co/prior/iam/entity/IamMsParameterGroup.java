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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "iam_ms_parameter_group")
public class IamMsParameterGroup extends BaseEntity<IamMsParameterGroup> {

	@Id
	@SequenceGenerator(name = "param_group_id_seq", sequenceName = "iam_ms_parameter_group_param_group_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "param_group_id_seq")
	private Long paramGroupId;
	private String paramGroup;
	private String paramEnDescription;
	private String paramLocalDescription;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "paramGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<IamMsParameterInfo> paramInfoSet;

}
