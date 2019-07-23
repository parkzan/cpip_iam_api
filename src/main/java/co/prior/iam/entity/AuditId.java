package co.prior.iam.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class AuditId implements Serializable {

    @GeneratedValue(generator = "audit_id_seq")
    @GenericGenerator(
            name = "audit_id_seq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "iam_audit_trail_audit_id_seq"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long auditId ;



    private Integer runningNo;


}
