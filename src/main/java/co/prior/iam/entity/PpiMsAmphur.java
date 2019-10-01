package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "PPI_MS_AMPHUR")
public class PpiMsAmphur extends BaseEntity<PpiMsAmphur>{

    @Id
    @SequenceGenerator(name = "amphur_id_seq",sequenceName = "PPI_MS_AMPHUR_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "amphur_id_seq")
    private long amphurId;
    private String amphurCode;
    private String amphurName;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "province_id")
    private PpiMsProvince ppiMsProvince;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ppiMsAmphur",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsTambol> ppiMsTambols ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ppiMsAmphur",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsSurvey> ppiMsSurveys ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "amphur",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsSource> ppiMsSources ;




}
