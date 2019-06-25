package co.prior.iam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "iam_ms_role_object")
public class IamMsRoleObject extends BaseEntity<IamMsRoleObject> {

    @Id
    @SequenceGenerator(name = "role_object_id_seq", sequenceName = "iam_ms_role_object_role_object_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_object_id_seq")
    private Long roleObjectId;

    @ManyToOne
    @JoinColumn(name = "system_id")
    private IamMsSystem iamMsSystem;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private IamMsRole iamMsRole;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private IamMsObject iamMsObject;
    
}
