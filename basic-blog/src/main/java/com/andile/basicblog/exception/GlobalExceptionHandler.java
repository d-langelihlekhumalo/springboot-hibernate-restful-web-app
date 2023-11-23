package com.andile.basicblog.exception;

import com.andile.basicblog.payload.ErrorDetails;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // This class will handle specific and global 'generic' exceptions

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), resourceNotFoundException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceExistsException.class)
    public ResponseEntity<ErrorDetails> handleResourceExistsException(ResourceExistsException resourceExistsException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), resourceExistsException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(APIException apiException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), apiException.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, apiException.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, WebRequest webRequest) {
        String message = "Invalid URL request. Please enter a valid URL request (in places where id's are required, please provide numbers, no symbols, alphabets or special characters allowed)";
        // methodArgumentTypeMismatchException.getMessage()
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException accessDeniedException, WebRequest webRequest) {
        String message = "Unauthorised access. Only authorised users can perform this action";
        // methodArgumentTypeMismatchException.getMessage()
        ErrorDetails errorDetails = new ErrorDetails(new Date(), message, webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
