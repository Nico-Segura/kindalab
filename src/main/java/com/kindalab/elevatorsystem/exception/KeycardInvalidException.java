package com.kindalab.elevatorsystem.exception;

public class KeycardInvalidException extends Exception {
    private static final String DEFAULT_MESSAGE = "Access Denied: A valid keycard is required to access this floor.";

    private static final String DEFAULT_PREFIX_MESSAGE = "Access Denied: ";

    public KeycardInvalidException() {
        super(DEFAULT_MESSAGE);
    }

    public KeycardInvalidException(String message) {
        super(DEFAULT_PREFIX_MESSAGE + message);
    }
}