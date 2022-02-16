package zw.co.metbank.coresalariessystem.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zw.co.metbank.coresalariessystem.exceptions.FileException;
import zw.co.metbank.coresalariessystem.files.FileInfo;
import zw.co.metbank.coresalariessystem.models.extras.SalaryCsvFileDocument;
import zw.co.metbank.coresalariessystem.models.extras.SalaryRequestCsvEntry;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SalaryCsvFilesHandlerService {


    @Value("${storage.uploads.files}")
    private String uploadsDirectory;


    public FileInfo createSalariesFile(String requestId, List<SalaryRequestCsvEntry> entryList){


        String filePath = uploadsDirectory + GlobalMethods.generateFilename(requestId)+".csv";
        try{

            Writer writer = Files.newBufferedWriter(Paths.get(filePath));

            StatefulBeanToCsv<SalaryRequestCsvEntry> csvWriter = new StatefulBeanToCsvBuilder<SalaryRequestCsvEntry>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .withOrderedResults(false)
                    .build();

            csvWriter.write(entryList);
            writer.close();
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFilePath(filePath);
            fileInfo.setFileSize(String.valueOf(Files.size(Path.of(filePath))));
            return  fileInfo;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public SalaryCsvFileDocument parseFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        return parseFile(new FileInputStream(file));
    }

    public SalaryCsvFileDocument parseFile(MultipartFile multipartFile) throws IOException {
        return parseFile(multipartFile.getInputStream());
    }
    private SalaryCsvFileDocument parseFile(InputStream inputStream){

        SalaryCsvFileDocument document = new SalaryCsvFileDocument();

            Reader reader = new BufferedReader(new InputStreamReader(inputStream));
            CsvToBean<SalaryRequestCsvEntry> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(SalaryRequestCsvEntry.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<SalaryRequestCsvEntry> entries = csvToBean.parse();

            if(entries.isEmpty())
                throw new FileException("No content read from file");


            document.setRecordEntries(entries);
            document.resolveDocument();


        return document;
    }
}
