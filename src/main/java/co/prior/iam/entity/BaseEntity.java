package co.prior.iam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public  class BaseEntity {

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
