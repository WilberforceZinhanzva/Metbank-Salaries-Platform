package zw.co.metbank.coresalariessystem.models.extras;

import lombok.Data;

@Data
public class FileData {
    private String fileName;
    private String fileSize;
    private byte[] bytes;
}
