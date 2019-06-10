package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "iam_ms_role")
@Getter
@Setter
public class RoleEntity {

    @Id
    @SequenceGenerator(name = "role_id_seq", sequenceName = "iam_ms_role_role_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_icon")
    private String roleIcon;

    @Column(name = "system_id")
    private Long systemId;

    @JsonIgnore
    @Column(name = "is_deleted", length = 1)
    private String isDeleted = "N";

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date",nullable = false)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate;
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;
    @Column(name = "updated_by", length = 50)
    private String updatedBy;




}
