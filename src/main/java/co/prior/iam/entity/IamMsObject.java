package co.prior.iam.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "iam_ms_object")
public class IamMsObject extends BaseEntity<IamMsObject> {

    @Id
    @SequenceGenerator(name = "object_id_seq", sequenceName = "iam_ms_object_object_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "object_id_seq")
    private Long objectId;
    private String objectCode;
    private String objectName;

    @ManyToOne
    @JoinColumn(name = "system_id")
    private IamMsSystem iamMsSystem;

    @JsonIgnore
    @OneToMany(mappedBy = "iamMsObject",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<IamMsRoleObject> iamMsRoleObjects ;

    @ManyToOne
    @JoinColumn(name = "object_parent_id")
    private IamMsObject objectParent;

    @JsonIgnore
    @OneToMany(mappedBy = "objectParent",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<IamMsObject>  objects;

}
