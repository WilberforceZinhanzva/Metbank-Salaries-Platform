package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="salary_disbursement_requests")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class SalaryDisbursementRequest  implements Serializable {

    @Id
    private String id;
    private LocalDateTime placedOn;
    @Enumerated(EnumType.STRING)
    private DisbursementRequestProcessing currentStage;
    @ManyToOne
    @JoinColumn(name="client_company")
    private ClientCompany company;

    @OrderBy("doneAt desc")
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<DisbursementProcessLogger> actionLogging = new ArrayList<>();


}
