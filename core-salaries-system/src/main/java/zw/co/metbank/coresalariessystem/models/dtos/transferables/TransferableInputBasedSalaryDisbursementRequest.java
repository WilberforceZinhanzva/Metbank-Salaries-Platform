package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementInput;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementProcessLogger;
import zw.co.metbank.coresalariessystem.models.entities.InputBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TransferableInputBasedSalaryDisbursementRequest implements Transferable {
    private String id;
    private LocalDateTime placedOn;
    private String currentStage;
    private TransferableGeneratedSalariesFile generatedSalariesFile;
    private List<TransferableDisbursementInput> disbursementInputs = new ArrayList<>();
    private List<TransferableDisbursementProcessLogger> actionLogging = new ArrayList<>();

    public TransferableInputBasedSalaryDisbursementRequest(InputBasedSalaryDisbursementRequest request){
        this.id = request.getId();
        this.placedOn = request.getPlacedOn();
        this.currentStage = request.getCurrentStage().toString();
        this.generatedSalariesFile = request.getGeneratedSalariesFile().serializeForTransfer();
        this.disbursementInputs = request.getDisbursementInputs().stream().map(DisbursementInput::serializeForTransfer).collect(Collectors.toList());
        this.actionLogging = request.getActionLogging().stream().map(DisbursementProcessLogger::serializeForTransfer).collect(Collectors.toList());
    }
}
