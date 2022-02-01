package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementProcessLogger;
import zw.co.metbank.coresalariessystem.models.entities.FileBasedSalaryDisbursementRequest;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TransferableFileBasedSalaryDisbursementRequest implements Transferable {
    private String id;
    private String placedOn;
    private String currentStage;
    private String companyName;
    private String companyId;
    private TransferableDisbursementFile disbursementFile;
    private List<TransferableDisbursementProcessLogger> actionLogging = new ArrayList<>();

    public TransferableFileBasedSalaryDisbursementRequest(FileBasedSalaryDisbursementRequest request){
        this.id = request.getId();
        this.placedOn = GlobalMethods.formatDate(request.getPlacedOn());
        this.currentStage = request.getCurrentStage().toString();
        this.companyName = request.getCompany().getName();
        this.companyId = request.getCompany().getId();
        this.disbursementFile = request.getDisbursementFile().serializeForTransfer();
        this.actionLogging = request.getActionLogging().stream().map(DisbursementProcessLogger::serializeForTransfer).collect(Collectors.toList());

    }

}
