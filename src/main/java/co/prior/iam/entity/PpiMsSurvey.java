package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "PPI_MS_SURVEY")
public class PpiMsSurvey extends BaseEntity<PpiMsSurvey>{

    @Id
    @SequenceGenerator(name = "survey_id_seq", sequenceName = "PPI_MS_SURVEY_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "survey_id_seq")
    private long surveyId;
    
    private String surveyName;

    private String surveyCode;
    private String taxId;
    private String address;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "tambol_id")
    private PpiMsTambol ppiMsTambol;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "amphur_id")
    private PpiMsAmphur ppiMsAmphur;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "province_id")
    private PpiMsProvince ppiMsProvince;
    private String postcode;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ppiMsSurvey",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsContactPerson> ppiMsContactPeople ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "survey",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsSource> ppiMsSources ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "survey",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<IamMsUser> iamMsUsers ;

}
