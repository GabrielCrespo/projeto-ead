package com.ead.course.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundExcepetion.class)
    public ResponseEntity<ErrorRecordResponse> handleNotFoundException(NotFoundExcepetion exception) {
        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage(), null);
        LOGGER.error("NotFoundExcepetion message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorRecordResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRecordResponse> handleValidationsException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.BAD_REQUEST.value(), "Error: Validation failed", errors);
        LOGGER.error("MethodArgumentNotValidException message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRecordResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRecordResponse> handleInvalidFormatException(HttpMessageNotReadableException exception) {
        Map<String, String> errors = new HashMap<>();

        if (exception.getCause() instanceof InvalidFormatException ifx
                && ifx.getTargetType() != null
                && ifx.getTargetType().isEnum()) {
            String fieldName = ifx.getPath().getLast().getFieldName();
            String errorMessage = exception.getMessage();
            errors.put(fieldName, errorMessage);
        }


        var errorRecordResponse = new ErrorRecordResponse(HttpStatus.BAD_REQUEST.value(), "Error: Invalid enum value", errors);
        LOGGER.error("HttpMessageNotReadableException message: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorRecordResponse);
    }


}
