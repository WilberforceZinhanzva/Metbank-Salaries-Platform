package zw.co.metbank.coresalariessystem.models.dtos.transferables;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.entities.GeneratedSalariesFile;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;

@Data
@NoArgsConstructor
public class TransferableGeneratedSalariesFile implements Transferable {

    private String id;
    private String fileSize;
    private String filePath;

    public TransferableGeneratedSalariesFile(GeneratedSalariesFile gf){
        this.id = gf.getId();
        this.fileSize = gf.getFileSize();
        this.filePath = gf.getFilePath();
    }
}
