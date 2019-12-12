package co.prior.iam.entity;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "iam_ms_object")
public class IamMsObject extends BaseEntity<IamMsObject> {

    @Id
    @SequenceGenerator(name = "object_id_seq", sequenceName = "iam_ms_object_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "object_id_seq")
    private Long objectId;
    private String objectCode;
    private String objectName;
    private String objectUrl;
    private Float sortingOrder;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "system_id")
    private IamMsSystem iamMsSystem;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "iamMsObject",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<IamMsRoleObject> iamMsRoleObjects ;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "object_parent_id")
    private IamMsObject objectParent;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "object_type")
    private IamMsParameterInfo objectType;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "objectParent",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private Set<IamMsObject>  objects;

}
