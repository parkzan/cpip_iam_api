package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name =  "sys_ms_system")
@Getter
@Setter
public class SystemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_id")
    private Integer systemID;

    @Column(name = "system_code")
    private String systemCode;

    @Column(name = "system_name")
    private String systemName;

    @Column(name = "system_icon")
    private String systemIcon;



    @JsonIgnore
    @Column(name = "is_deleted", length = 1)
    private String isDeleted = "N";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 19, nullable = false)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", length = 19)
    private Date updatedDate;
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;
    @Column(name = "updated_by", length = 50)
    private String updatedBy;


}
