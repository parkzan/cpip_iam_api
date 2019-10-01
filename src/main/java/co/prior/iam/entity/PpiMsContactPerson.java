package co.prior.iam.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "PPI_MS_CONTACT_PERSON")
@AllArgsConstructor
@NoArgsConstructor
public class PpiMsContactPerson extends BaseEntity<PpiMsContactPerson>{

    @Id
    @SequenceGenerator(name = "contact_person_id_seq",sequenceName = "PPI_MS_CONTACT_PERSON_SEQ",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "contact_person_id_seq")
    private Long contactPersonId;
    private String titleId;
    private String contactName;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "survey_id")
    private PpiMsSurvey ppiMsSurvey;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "source_id")
    private PpiMsSource ppiMsSource;

    @NonNull
    private String telephoneNo;
    private String faxNo;
    private String mobileNo;

    @Pattern(regexp="^(.+)@(.+)$|^\\s*$",message = "invalid email pattern")
    private String email;


}
