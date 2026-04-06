package de.poker.api.exceptions;

public class TableFullException extends RuntimeException {
    public TableFullException(String tableId) {
        super("Table with id: " + tableId + " is full");
    }
}
