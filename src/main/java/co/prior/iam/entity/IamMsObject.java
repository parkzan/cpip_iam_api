package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "iam_ms_object")
@Data
public class IamMsObject extends  BaseEntity {

    @Id
    @SequenceGenerator(name = "object_id_seq", sequenceName = "iam_ms_object_object_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "object_id_seq")
    private Long objectId;
    private String objectCode;
    private String objectName;


    @ManyToOne
    @JoinColumn(name = "system_id")
    @JsonIgnore
    private IamMsSystem iamMsSystem;

    @OneToMany(mappedBy = "iamMsObject",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsRoleObject> iamMsRoleObjects ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "object_parent_id")
    private IamMsObject objectParent;

    @OneToMany(mappedBy = "objectParent",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsObject>  objects;

}
