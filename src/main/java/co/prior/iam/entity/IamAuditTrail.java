package co.prior.iam.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "iam_audit_trail")
public class IamAuditTrail {


	@EmbeddedId
	private AuditId auditId;

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
    private LocalDateTime createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;


    
}

