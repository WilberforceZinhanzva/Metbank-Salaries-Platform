package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementProcessLogger;
import zw.co.metbank.coresalariessystem.models.entities.SalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TransferableSalaryDisbursementRequest implements Transferable {
    private String id;
    private LocalDateTime placedOn;
    private String currentStage;
    private TransferableDisbursementFile disbursementFile;
    private List<TransferableDisbursementProcessLogger> actionLogging = new ArrayList<>();

    public TransferableSalaryDisbursementRequest(SalaryDisbursementRequest request){
        this.id = request.getId();
        this.placedOn = request.getPlacedOn();
        this.currentStage = request.getCurrentStage().toString();
        this.disbursementFile = request.getDisbursementFile().serializeForTransfer();
        this.actionLogging = request.getActionLogging().stream().map(DisbursementProcessLogger::serializeForTransfer).collect(Collectors.toList());

    }
}
