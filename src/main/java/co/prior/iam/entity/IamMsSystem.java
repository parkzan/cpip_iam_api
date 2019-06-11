package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "iam_ms_system")
@Getter
@Setter
public class IamMsSystem {
    @Id
    @SequenceGenerator(name = "system_id_seq", sequenceName = "iam_ms_system_system_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "system_id_seq")

    private Long systemId;


    private String systemCode;


    private String systemName;


    private String systemIcon;

    @JsonIgnore
    protected String isDeleted = "N";
    @CreationTimestamp
    @JsonIgnore
    protected Date createdDate;
    @UpdateTimestamp
    @JsonIgnore
    protected Date updatedDate;
    @JsonIgnore
    protected String createdBy;
    @JsonIgnore
    protected String updatedBy;

}
