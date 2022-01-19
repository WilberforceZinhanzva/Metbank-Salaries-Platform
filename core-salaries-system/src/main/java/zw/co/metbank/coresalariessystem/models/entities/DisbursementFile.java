package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableDisbursementFile;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="disbursement_file")
public class DisbursementFile implements Serializable {
    @Id
    private String id;
    @Column(name="file_size")
    private String fileSize;
    @Column(name = "file_path")
    private String filePath;
    @OneToOne(mappedBy = "disbursementFile")
    private SalaryDisbursementRequest disbursementRequest;

    @Override
    public TransferableDisbursementFile serializeForTransfer() {
        return new TransferableDisbursementFile(this);
    }
}
