package ru.clevertec.ecl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.clevertec.ecl.dto.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse();
        response.setErrorMessage("Requested resource ( " + ex.getResourceType() + " ) not found (uuid = " + ex.getResourceId() + ")");
        response.setErrorCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleUUIDFormatException(MethodArgumentTypeMismatchException e) {
        ApiErrorResponse response = new ApiErrorResponse();
        if (e.getValue() instanceof String && e.getName().equals("uuid")) {
            response.setErrorMessage("Неверный формат UUID: " + e.getValue());
            response.setErrorCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.internalServerError().build();
    }
}
