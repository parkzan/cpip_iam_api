package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "PPI_MS_SOURCE")
public class PpiMsSource extends BaseEntity<PpiMsSource> {
	    @Id
	    @SequenceGenerator(name = "source_id_seq", sequenceName = "PPI_MS_SOURCE_SEQ",allocationSize = 1)
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "source_id_seq")
	    private Long sourceId;
	    private String sourceName;
	    private String sourceCode;

	    @ToString.Exclude
		@ManyToOne
		@JoinColumn(name = "survey_id")
	    private PpiMsSurvey survey;

	    private String taxId;
	    private String address;

		@ToString.Exclude
		@ManyToOne
		@JoinColumn(name = "tambol_id")
	    private PpiMsTambol tambol;

		@ToString.Exclude
		@ManyToOne
		@JoinColumn(name = "amphur_id")
	    private PpiMsAmphur amphur;

		@ToString.Exclude
		@ManyToOne
		@JoinColumn(name = "province_id")
	    private PpiMsProvince province;

	    private String postcode;

	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "ppiMsSource",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
	private Set<PpiMsContactPerson> ppiMsContactPeople ;
}
