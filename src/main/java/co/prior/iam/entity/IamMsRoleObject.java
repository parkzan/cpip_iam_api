package co.prior.iam.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "iam_ms_role_object")
@Data
public class IamMsRoleObject extends BaseEntity{


    @Id
    @SequenceGenerator(name = "role_object_id_seq", sequenceName = "iam_ms_role_object_role_object_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_object_id_seq")
    private Long roleObjectId;

    private Long systemId;

    private Long roleId;

    private Long objectId;
}
