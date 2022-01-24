package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableFileBasedSalaryDisbursementRequest;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
public class FileBasedSalaryDisbursementRequest extends SalaryDisbursementRequest {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "disbursement_file")
    private DisbursementFile disbursementFile;

    @Override
    public TransferableFileBasedSalaryDisbursementRequest serializeForTransfer() {
        return new TransferableFileBasedSalaryDisbursementRequest(this);
    }
}
