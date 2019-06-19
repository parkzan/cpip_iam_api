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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "iam_ms_system")
public class IamMsSystem extends BaseEntity<IamMsSystem> {
	
    @Id
    @SequenceGenerator(name = "system_id_seq", sequenceName = "iam_ms_system_system_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_id_seq")
    private Long systemId;

    private String systemCode;

    private String systemName;

    private String systemIcon;

    @JsonIgnore
    @OneToMany(mappedBy = "iamMsSystem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<IamMsRole> iamMsRoles;
    
}
