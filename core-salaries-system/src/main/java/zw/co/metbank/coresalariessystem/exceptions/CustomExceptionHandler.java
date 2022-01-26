package zw.co.metbank.coresalariessystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value={FileException.class})
    public ResponseEntity<CustomException> handleFileException(FileException e){
        CustomException exception = new CustomException(e.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value={ResourceNotFoundException.class})
    public ResponseEntity<CustomException> handleResourceNotFoundException(ResourceNotFoundException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exception,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value={ResourceAlreadyExistsException.class})
    public ResponseEntity<CustomException> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value={InvalidConsumableException.class})
    public ResponseEntity<CustomException> handleInvalidConsumableException(InvalidConsumableException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value={FileCreationException.class})
    public ResponseEntity<CustomException> handleFileCreationException(FileCreationException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
    }


    @ExceptionHandler(value = {SalaryProcessingException.class})
    public ResponseEntity<CustomException> handleSalaryProcessingException(SalaryProcessingException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
    }
}
