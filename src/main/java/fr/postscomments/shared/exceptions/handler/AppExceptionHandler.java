package fr.postscomments.shared.exceptions.handler;

import fr.postscomments.shared.exceptions.EntityAlreadyExist;
import fr.postscomments.shared.exceptions.EntityNotFoundException;
import fr.postscomments.shared.exceptions.EntityNotValidate;
import fr.postscomments.shared.exceptions.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class AppExceptionHandler {
    //HttpMessageNotReadableException
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(400)
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(404)
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EntityAlreadyExist.class})
    public ResponseEntity<Object> entityAlreadyExist(EntityAlreadyExist ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(403)
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(value = EntityNotValidate.class)
    public ResponseEntity<Object> entityNotValid(EntityNotValidate ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(404)
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpClientErrorException.BadRequest.class)
    public ResponseEntity<Object> badRequest(HttpClientErrorException.BadRequest ex) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(400)
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        // Créer un objet contenant les détails de l'erreur de validation
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        // Créer une réponse d'erreur contenant les détails de l'erreur de validation
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(errors.toString())
                .timestamp(new Date())
                .code(400)
                .build();

        // Renvoyer une réponse HTTP 400 Bad Request contenant la réponse d'erreur
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
