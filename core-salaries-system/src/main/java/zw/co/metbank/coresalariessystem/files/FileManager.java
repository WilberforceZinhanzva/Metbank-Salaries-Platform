package zw.co.metbank.coresalariessystem.files;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileManager {
    Map<String,String> saveFile(String filename , MultipartFile multipartFile) throws IOException;
    Boolean deleteFile(String path);
}
