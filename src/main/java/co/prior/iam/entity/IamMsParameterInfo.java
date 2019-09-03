package co.prior.iam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "iam_ms_parameter_info")
public class IamMsParameterInfo extends BaseEntity<IamMsParameterInfo> {

	@Id
	@SequenceGenerator(name = "param_info_id_seq", sequenceName = "iam_ms_parameter_info_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "param_info_id_seq")
	private Long paramInfoId;
	private String paramInfo;
	private String paramEnDescription;
	private String paramLocalDescription;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "param_group_id")
	private IamMsParameterGroup paramGroup;

}
