package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "iam_ms_role")
@Getter
@Setter
public class IamMsRole extends BaseEntity{

    @Id
    @SequenceGenerator(name = "role_id_seq", sequenceName = "iam_ms_role_role_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_seq")
    private Long roleId;


    private String roleCode;


    private String roleName;


    private String roleIcon;

    @ManyToOne
    @JoinColumn(name = "system_id")
    @JsonIgnore
    private IamMsSystem iamMsSystem;

    @OneToMany(mappedBy = "iamMsRole",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<IamMsRoleObject> iamMsRoleObjects ;



}
