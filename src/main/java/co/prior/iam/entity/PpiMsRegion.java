package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "PPI_MS_REGION")
public class PpiMsRegion extends BaseEntity<PpiMsRegion> {

    @Id
    @SequenceGenerator(name = "region_id_seq", sequenceName = "PPI_MS_REGION_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "region_id_seq")
    private long regionId;
    private String regionCode;
    private String regionName;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "ppiMsRegion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<PpiMsProvince> ppiMsProvinces;

}