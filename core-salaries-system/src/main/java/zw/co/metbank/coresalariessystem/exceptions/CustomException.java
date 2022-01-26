package zw.co.metbank.coresalariessystem.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
public class CustomException {
    private final String message;
    private final HttpStatus httpStatus;
    private final String timestamp;

    public CustomException(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = ZonedDateTime.now().toString();
    }
}

