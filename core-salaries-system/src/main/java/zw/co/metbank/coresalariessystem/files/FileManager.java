package zw.co.metbank.coresalariessystem.files;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileManager {
    FileInfo saveFile(String filename , MultipartFile multipartFile) throws IOException;
    Boolean deleteFile(String path);
}
