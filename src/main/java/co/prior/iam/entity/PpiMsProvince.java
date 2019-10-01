package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "PPI_MS_PROVINCE")
public class PpiMsProvince extends BaseEntity<PpiMsProvince> {

    @Id
    @SequenceGenerator(name = "province_id_seq",sequenceName = "PPI_MS_PROVINCE_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "province_id_seq")
    private long provinceId;
    private String provinceCode;
    private String provinceName;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "region_id")
    private PpiMsRegion ppiMsRegion;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ppiMsProvince",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsAmphur> ppiMsAmphurs ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ppiMsProvince",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsSurvey> ppiMsSurveys ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "province",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsSource> ppiMsSources ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "province",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<IamMsUser> iamMsUsers ;

}
