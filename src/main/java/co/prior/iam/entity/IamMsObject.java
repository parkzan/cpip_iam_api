package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "iam_ms_object")
@Getter
@Setter
public class IamMsObject {

    @Id
    @SequenceGenerator(name = "object_id_seq", sequenceName = "iam_ms_object_object_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "object_id_seq")
    private Long objectId;
    private String objectCode;
    private String objectName;
    private Long systemId;
    private Long objectParentId;

    @JsonIgnore
    protected String isDeleted = "N";
    @CreationTimestamp
    @JsonIgnore
    protected LocalDateTime createdDate = LocalDateTime.now();
    @UpdateTimestamp
    @JsonIgnore
    protected LocalDateTime updatedDate;
    @JsonIgnore
    protected String createdBy ="ADMIN";
    @JsonIgnore
    protected String updatedBy;



}
