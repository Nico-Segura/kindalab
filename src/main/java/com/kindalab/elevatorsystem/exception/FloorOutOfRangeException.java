package com.kindalab.elevatorsystem.exception;

public class FloorOutOfRangeException extends Exception {
    private static final String DEFAULT_MESSAGE = "Floor invalid. The requested floor is out of range.";

    public FloorOutOfRangeException() {
        super(DEFAULT_MESSAGE);
    }

    public FloorOutOfRangeException(String message) {
        super(message);
    }
}