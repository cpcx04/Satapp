package triana.salesianos.edu.SataApp.error;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import triana.salesianos.edu.SataApp.error.impl.ApiValidationSubError;
import triana.salesianos.edu.SataApp.exception.Inventory.RelatedTicketsException;
import triana.salesianos.edu.SataApp.exception.User.UserValidationException;
import triana.salesianos.edu.SataApp.security.errorhandling.JwtTokenException;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ApiValidationSubError> validationErrors = exception.getBindingResult().getAllErrors().stream()
                .map(ApiValidationSubError::fromObjectError)
                .toList();
        ErrorResponse er = ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage())
                .title("Invalid data error")
                .type(URI.create("https://api.bikeapp.com/errors/not-valid"))
                .property("Fields errors", validationErrors)
                .build();
        return ResponseEntity.status(status)
                .body(er);
    }

    @ExceptionHandler({UserValidationException.class })
    private static ErrorResponse handleUserValidationException(UserValidationException exception) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage())
                .title("The user has to be validated by a admin")
                .type(URI.create("https://api.sataapp.com/errors/validation"))
                .property("date", LocalDateTime.now().format(formatter))
                .build();
    }
    @ExceptionHandler({RelatedTicketsException.class })
    private static ErrorResponse handleRelatedTicketsException(RelatedTicketsException exception) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage())
                .title("You can't delete a iventory item with a ticket related,solve it first")
                .type(URI.create("https://api.sataapp.com/errors/tickets"))
                .property("date", LocalDateTime.now().format(formatter))
                .build();
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ErrorResponse handleNotFoundException(EntityNotFoundException exception) {
        return ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage())
                .title("Entity not found")
                .type(URI.create("https://api.sataapp.com/errors/not-found"))
                .property("timestamp", Instant.now())
                .build();
    }
    @ExceptionHandler({ AuthenticationException.class })
    public ErrorResponse handleAuthenticationException(AuthenticationException exception) {
        return ErrorResponse.builder(exception, HttpStatus.UNAUTHORIZED, exception.getMessage())
                .title("AUTHENTICATION")
                .type(URI.create("https://api.sataapp.com/errors/unauthorized-user"))
                .property("timestamp", Instant.now())
                .build();

    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
        return ErrorResponse.builder(exception, HttpStatus.FORBIDDEN, exception.getMessage())
                .title("ACCESS DENIED")
                .type(URI.create("https://api.sataapp.com/errors/access-denied"))
                .property("timestamp", Instant.now())
                .build();

    }


    @ExceptionHandler({JwtTokenException.class})
    public ErrorResponse handleTokenException(JwtTokenException exception) {
        return ErrorResponse.builder(exception, HttpStatus.FORBIDDEN, exception.getMessage())
                .title("TOKEN INVALID")
                .type(URI.create("https://api.sataapp.com/errors/invalid-token"))
                .property("timestamp", Instant.now())
                .build();
    }

}
