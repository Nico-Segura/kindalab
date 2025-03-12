package com.kindalab.elevatorsystem.exception;

public class ActivatedAlarmException extends Exception {
    private static final String DEFAULT_MESSAGE = "ACTIVED ALARM! Elevator system is shut down. ";

    public ActivatedAlarmException() {
        super(DEFAULT_MESSAGE);
    }

    public ActivatedAlarmException(String message) {
        super(DEFAULT_MESSAGE + message);
    }
}