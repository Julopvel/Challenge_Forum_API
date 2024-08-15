package forum.api.infrastructure.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//With @RestControllerAdvice, we make sure to isolate exceptions
@RestControllerAdvice
public class ErrorsHandler {

    //When EntityNotFoundException rises, the @ExceptionHandler will make sure to
    //display the below message (return ResponseEntity.notFound().build())
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> error404Handler(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> error400Handler(){

        return ResponseEntity.badRequest().build();

    }


}
