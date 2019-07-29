package co.prior.iam.entity;



import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


import javax.persistence.*;
import java.io.Serializable;


@Data
public class AuditId implements Serializable {

//
//    @GenericGenerator(
//            name = "audit_id_seq",
//            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
//            parameters = {
//                    @Parameter(name = "sequence_name", value = "iam_audit_trail_audit_id_seq"),
//                    @Parameter(name = "initial_value", value = "1"),
//                    @Parameter(name = "increment_size", value = "1")
//            }
//    )
//    @GeneratedValue(generator = "audit_id_seq")
    private Long auditId ;



    private Integer runningNo;


    public AuditId(Long auditId, Integer runningNo) {
        this.auditId = auditId;
        this.runningNo = runningNo;
    }

    public AuditId(){}
}
