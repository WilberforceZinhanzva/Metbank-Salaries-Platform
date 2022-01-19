package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableActionLogger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name="disbursement_file_actions_logger")
public class DisbursementFileActionLogger extends ActionLogger{
    @Override
    public TransferableActionLogger serializeForTransfer() {
        return new TransferableActionLogger(this);
    }
}
