package de.poker.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TableNotFoundException extends RuntimeException {

    public TableNotFoundException(String tableId) {
        super("Table with id: " + tableId + " does not exist");
    }
}