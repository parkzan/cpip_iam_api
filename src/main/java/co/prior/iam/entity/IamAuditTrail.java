package co.prior.iam.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "iam_audit_trail")
public class IamAuditTrail {

	@Id
    @SequenceGenerator(name = "audit_id_seq", sequenceName = "iam_audit_trail_audit_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_id_seq")
    private Long auditId;
	private Integer runningNo;
	private String tableName;
	private Long primaryKey;
	private String columnName;
	private String oldValue;
	private String newValue;
	private String oldDescription;
	private String newDescription;
	private String isNew;
	private String isFk;
	
	@JsonIgnore
    @CreationTimestamp
    protected LocalDateTime createdDate;

    @JsonIgnore
    @CreatedBy
    protected String createdBy;
    
}
