package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.metbank.coresalariessystem.models.extras.FileData;
import zw.co.metbank.coresalariessystem.services.FileDownloaderService;

@RestController
@RequestMapping("/api/v1/file-downloader")
public class FileDownloaderController {

    @Autowired
    private FileDownloaderService fileDownloaderService;

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(String filePath){
        FileData fileData = fileDownloaderService.download(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename="+fileData.getFileName());
        headers.set(HttpHeaders.CONTENT_TYPE,"application/csv");

        return new ResponseEntity<>(fileData.getBytes(),headers, HttpStatus.OK);
    }
}
