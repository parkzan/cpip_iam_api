package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "PPI_MS_TAMBOL")
public class PpiMsTambol extends BaseEntity<PpiMsTambol> {

    @Id
    @SequenceGenerator(name = "tambol_id_seq",sequenceName = "PPI_MS_TAMBOL_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "tambol_id_seq")
    private long tambolId;
    private String tambolCode;
    private String tambolName;
    private String postcode;


    @ToString.Exclude
    @OneToOne
    @JoinColumn(name = "amphur_id")
    private PpiMsAmphur ppiMsAmphur;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ppiMsTambol",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsSurvey> ppiMsSurveys ;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "tambol",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<PpiMsSource> ppiMsSources ;

}
