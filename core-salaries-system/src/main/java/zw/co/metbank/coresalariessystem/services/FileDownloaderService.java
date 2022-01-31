package zw.co.metbank.coresalariessystem.services;

import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.models.extras.FileData;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileDownloaderService {


    public FileData download(String filePath){
        File file = new File(filePath);
        FileData fileData = new FileData();
        fileData.setFileName(file.getName());

        try{
            fileData.setFileSize(String.valueOf(Files.size(Paths.get(file.getPath()))));
            FileInputStream fileInputStream = new FileInputStream(file);
            fileData.setBytes(fileInputStream.readAllBytes());
        }catch (Exception e){

        }
        return fileData;
    }
}
