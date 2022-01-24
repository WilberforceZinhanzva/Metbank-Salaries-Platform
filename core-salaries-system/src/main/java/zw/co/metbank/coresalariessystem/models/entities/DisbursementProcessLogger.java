package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableDisbursementProcessLogger;
import zw.co.metbank.coresalariessystem.models.enums.DisbursementRequestProcessing;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="disbursement_process_logging")
public class DisbursementProcessLogger implements Serializable {
    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private DisbursementRequestProcessing stage;
    @Column(name="done_by")
    private String doneBy;
    @Column(name="actor_id")
    private String actorId;
    @Column(name="done_at")
    private LocalDateTime doneAt;
    @ManyToOne
    @JoinColumn(name="disbursement_request")
    private SalaryDisbursementRequest disbursementRequest;

    public DisbursementProcessLogger(){
        this.id = GlobalMethods.generateId("DISBLOG");
    }


    @Override
    public TransferableDisbursementProcessLogger serializeForTransfer() {
        return new TransferableDisbursementProcessLogger(this);
    }
}
