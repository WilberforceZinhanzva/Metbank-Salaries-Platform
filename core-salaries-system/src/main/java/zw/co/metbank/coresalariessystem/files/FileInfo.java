package zw.co.metbank.coresalariessystem.files;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileInfo {
    private String fileSize;
    private String filePath;
    private String originalFileName;
}
