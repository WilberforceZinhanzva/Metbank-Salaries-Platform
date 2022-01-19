package zw.co.metbank.coresalariessystem.files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import zw.co.metbank.coresalariessystem.exceptions.FileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LocalStorageFileManager implements FileManager {

    @Value("${storage.uploads.files}")
    private String uploadsDirectory;

    @Override
    public Map<String, String> saveFile(String filename, MultipartFile multipartFile) throws IOException,FileException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

       if(! new File(uploadsDirectory).exists()){
           log.info("Creating uploads directory");
           boolean dirCreated = new File(uploadsDirectory).mkdir();
           if (dirCreated) {
               log.info("Uploads directory created successfully");
           } else {
               log.info("Failed to create uploads directory");
               throw new FileException("Failed to create uploads directory");
           }

       }

       File newFile = new File(uploadsDirectory.concat(filename+fileExtension));
       newFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(filename.getBytes());
        fileOutputStream.close();

        Map<String,String> fileDetails = new HashMap<>();
        fileDetails.putIfAbsent("FilePath",newFile.getPath());
        fileDetails.putIfAbsent("FileSize", String.valueOf(Files.size(Path.of(newFile.getPath()))));
        return fileDetails;
    }

    @Override
    public Boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }
}
