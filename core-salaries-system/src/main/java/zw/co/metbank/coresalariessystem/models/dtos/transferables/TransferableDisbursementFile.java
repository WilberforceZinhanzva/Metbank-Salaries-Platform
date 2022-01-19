package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.DisbursementFile;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferableDisbursementFile implements Transferable {
    private String id;
    private String fileSize;
    private String filePath;

    public TransferableDisbursementFile(DisbursementFile file){
        this.id = file.getId();
        this.fileSize = file.getFileSize();
        this.filePath = file.getFilePath();
    }
}
