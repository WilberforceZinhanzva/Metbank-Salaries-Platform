package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zw.co.metbank.coresalariessystem.models.extras.SalaryCsvFileDocument;
import zw.co.metbank.coresalariessystem.services.SalaryCsvFilesHandlerService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/file-handler")
public class SalaryCsvFilesHandlerController {

    @Autowired
    private SalaryCsvFilesHandlerService salaryCsvFilesHandlerService;

    @GetMapping("/path-method-document")
    public ResponseEntity<SalaryCsvFileDocument> parseFile(@RequestParam String filePath){
        SalaryCsvFileDocument document = new SalaryCsvFileDocument();
        try {
            document = salaryCsvFilesHandlerService.parseFile(filePath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  new ResponseEntity<>(document, HttpStatus.OK);

    }

    
}
