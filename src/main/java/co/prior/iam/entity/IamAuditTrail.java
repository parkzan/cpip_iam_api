package co.prior.iam.entity;


import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "iam_audit_trail")
@IdClass(AuditId.class)
public class IamAuditTrail {


	@Id
	@GenericGenerator(
			name = "audit_id_seq",
			strategy = "enhanced-sequence",
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequence_name", value = "iam_audit_trail_audit_id_seq"),
					@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
					@org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "0"),

			}
	)
	@GeneratedValue(generator = "audit_id_seq")
	private Long auditId;

	@Id
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
    private LocalDateTime createdDate;

    @JsonIgnore
    @CreatedBy
    private String createdBy;


    
}

