package ru.clevertec.ecl.exception;

import liquibase.exception.DatabaseException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * Глобальный обработчик исключений для обработки исключительных ситуаций в приложении.
 * Этот класс предоставляет методы для обработки различных исключений, возникающих во время выполнения HTTP-запросов.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик исключения ResourceNotFoundException.
     *
     * @param ex Исключение ResourceNotFoundException, которое нужно обработать.
     * @return ResponseEntity с объектом ApiErrorResponse, содержащим информацию об ошибке и статус код 404 (Not Found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiErrorResponse response = new ApiErrorResponse();

        response.setErrorMessage(ex.getMessage());
        response.setErrorCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Обработчик исключения MethodArgumentTypeMismatchException, связанного с неверным форматом UUID.
     *
     * @param e Исключение MethodArgumentTypeMismatchException, которое нужно обработать.
     * @return ResponseEntity с объектом ApiErrorResponse, содержащим информацию об ошибке и статус код 400 (Bad Request).
     */
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

    /**
     * Обработчик исключения DatabaseException.
     *
     * @param ex Исключение DatabaseException, которое нужно обработать.
     * @return ResponseEntity с объектом ApiErrorResponse, содержащим информацию об ошибке и статус код 500 (Internal Server Error).
     */
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabaseException(DatabaseException ex) {
        ApiErrorResponse response = new ApiErrorResponse();

        response.setErrorMessage("Ошибка базы данных: " + ex.getMessage());
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Обработчик исключения PropertyValueException, связанного с Hibernate.
     *
     * @param ex Исключение PropertyValueException, которое нужно обработать.
     * @return ResponseEntity с объектом ApiErrorResponse, содержащим информацию об ошибке и статус код 500 (Internal Server Error).
     */
    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ApiErrorResponse> handleHibernateError(PropertyValueException ex) {
        ApiErrorResponse response = new ApiErrorResponse();

        response.setErrorMessage("Ошибка в Hibernate: " + ex.getMessage());
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
