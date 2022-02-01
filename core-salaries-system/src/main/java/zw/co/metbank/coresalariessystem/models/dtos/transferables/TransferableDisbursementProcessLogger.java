package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementProcessLogger;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

@Data
@NoArgsConstructor
public class TransferableDisbursementProcessLogger implements Transferable {
    private String id;
    private String stage;
    private String doneBy;
    private String actorId;
    private String doneAt;

    public TransferableDisbursementProcessLogger(DisbursementProcessLogger logger){
        this.id = logger.getId();
        this.stage = logger.getStage().toString();
        this.doneBy = logger.getDoneBy();
        this.actorId = logger.getActorId();
        this.doneAt = GlobalMethods.formatDate(logger.getDoneAt());

    }
}
