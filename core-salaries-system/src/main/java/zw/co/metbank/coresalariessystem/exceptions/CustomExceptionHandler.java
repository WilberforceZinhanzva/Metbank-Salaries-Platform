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

    @ExceptionHandler(value={WrongFileException.class})
    public ResponseEntity<CustomException> handleWrongFileException(WrongFileException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value={InvalidPasswordException.class})
    public ResponseEntity<CustomException> handleInvalidPasswordException(InvalidPasswordException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value={ActionForbidden.class})
    public ResponseEntity<CustomException> handleForbiddenActionException(ActionForbidden e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(exception,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value={FileParseException.class})
    public ResponseEntity<CustomException> handleFileParseException(FileParseException e){
        CustomException exception = new CustomException(e.getMessage(),HttpStatus.CONFLICT);
        return new ResponseEntity<>(exception,HttpStatus.CONFLICT);
    }
}
