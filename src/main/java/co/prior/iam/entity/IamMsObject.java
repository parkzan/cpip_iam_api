package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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


    private Long systemId;

    private Long objectParentId;

//    @ManyToOne
//    @JoinColumn(name = "system_id")
//    private IamMsSystem iamMsSystem;





}
