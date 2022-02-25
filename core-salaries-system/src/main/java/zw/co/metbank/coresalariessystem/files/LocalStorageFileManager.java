package zw.co.metbank.coresalariessystem.files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import zw.co.metbank.coresalariessystem.exceptions.FileException;
import zw.co.metbank.coresalariessystem.exceptions.WrongFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Slf4j
@Component
public class LocalStorageFileManager implements FileManager {

    @Value("${storage.uploads.files}")
    private String uploadsDirectory;
    @Value("${storage.uploads.remittance}")
    private String remittanceDirectory;

    @Override
    public FileInfo saveFile(String filename, MultipartFile multipartFile) throws IOException,FileException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

        if(!fileExtension.contentEquals(".csv"))
            throw new WrongFileException("Unacceptable file type! Only csv files are required!");


        File newFile = new File(uploadsDirectory.concat(filename+fileExtension));
        newFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();


        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSize(String.valueOf(Files.size(Path.of(newFile.getPath()))));
        fileInfo.setFilePath(newFile.getPath());
        fileInfo.setOriginalFileName(originalFilename);
        return fileInfo;
    }

    public FileInfo saveFile(File file) throws IOException, FileException{
        String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));

        if(!fileExtension.contentEquals(".csv"))
            throw new WrongFileException("Unacceptable file type! Only csv files are required!");
        File newFile = new File(remittanceDirectory.concat(file.getName()));
        newFile.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        fileOutputStream.write(Files.readAllBytes(file.toPath()));
        fileOutputStream.close();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSize(String.valueOf(Files.size(Path.of(newFile.getPath()))));
        fileInfo.setFilePath(newFile.getPath());
        fileInfo.setOriginalFileName(file.getName());
        return fileInfo;

    }

    @Override
    public Boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }
}
