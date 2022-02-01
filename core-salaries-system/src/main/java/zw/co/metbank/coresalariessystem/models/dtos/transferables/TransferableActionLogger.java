package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.ActionLogger;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

@Data
@NoArgsConstructor
public class TransferableActionLogger implements Transferable {
    private String id;
    private String action;
    private String actionDoneBy;
    private String actorId;
    private String actionDoneAt;

    public TransferableActionLogger(ActionLogger actionLogger){
        this.id = actionLogger.getId();
        this.action = actionLogger.getAction();
        this.actionDoneBy = actionLogger.getActionDoneBy();
        this.actorId = actionLogger.getActorId();
        this.actionDoneAt = GlobalMethods.formatDate(actionLogger.getActionDoneAt());
    }
}
