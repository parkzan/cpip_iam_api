package co.prior.iam.entity;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.prior.iam.model.AnswerFlag;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity<T> {

    @Column(updatable = false, insertable = true)
    @JsonIgnore
    @CreationTimestamp
    protected LocalDateTime createdDate;

    @Column(updatable = false, insertable = true)
    @JsonIgnore
    @CreatedBy
    protected String createdBy;

    @Column(updatable = true , insertable = false)
    @JsonIgnore
    @UpdateTimestamp
    protected LocalDateTime updatedDate ;

    @Column(updatable = true , insertable = false)
    @JsonIgnore
    @LastModifiedBy
    protected String updatedBy;
    
    @JsonIgnore
    protected String isDeleted = AnswerFlag.N.toString();

    @JsonIgnore
    @Transient
    protected T previousState;
    
    @SuppressWarnings("unchecked")
	@PostLoad
    private void setPreviousState() throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	String serialized = mapper.writeValueAsString(this);
    	this.previousState = (T) mapper.readValue(serialized, this.getClass());
    }

    public T getPreviousState(){
    	return previousState;
    }



}
