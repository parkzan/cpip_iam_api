package co.prior.iam.entity;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "iam_ms_role_object")
public class IamMsRoleObject extends BaseEntity<IamMsRoleObject> {

    @Id
    @SequenceGenerator(name = "role_object_id_seq", sequenceName = "iam_ms_role_object_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_object_id_seq")
    private Long roleObjectId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "system_id")
    private IamMsSystem iamMsSystem;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "role_id")
    private IamMsRole iamMsRole;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "object_id")
    private IamMsObject iamMsObject;
    
}
