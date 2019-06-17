package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table (name = "iam_ms_role")
@Getter
@Setter
public class IamMsRole{

    @Id
    @SequenceGenerator(name = "role_id_seq", sequenceName = "iam_ms_role_role_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Long roleId;


    private String roleCode;


    private String roleName;


    private String roleIcon;


    private Long systemId;



    @JsonIgnore
    protected String isDeleted = "N";
    @CreationTimestamp
    @JsonIgnore
    protected LocalDateTime createdDate = LocalDateTime.now();
    @UpdateTimestamp
    @JsonIgnore
    protected LocalDateTime updatedDate;
    @JsonIgnore
    protected String createdBy = "ADMIN";
    @JsonIgnore
    protected String updatedBy;


}
