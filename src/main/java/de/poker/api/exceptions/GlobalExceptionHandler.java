package de.poker.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<?> handleTableNotFound(TableNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "TABLE_NOT_FOUND",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(TableFullException.class)
    public ResponseEntity<?> handleTableFull(TableFullException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(Map.of(
                        "error", "TABLE_FULL",
                        "message", ex.getMessage()
                ));
    }
}
