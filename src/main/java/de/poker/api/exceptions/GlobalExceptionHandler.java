package de.poker.api.exceptions;

import de.poker.api.TableController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<?> handleTableNotFound(TableNotFoundException ex) {

        LOG.warn("Table not found: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "TABLE_NOT_FOUND",
                        "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(TableFullException.class)
    public ResponseEntity<?> handleTableFull(TableFullException ex) {

        LOG.warn("Table full: {}", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 409
                .body(Map.of(
                        "error", "TABLE_FULL",
                        "message", ex.getMessage()
                ));
    }
}
