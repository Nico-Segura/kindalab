package com.kindalab.elevatorsystem.exception;

public class WeightLimitExceededException extends Exception {
    private static final String DEFAULT_MESSAGE = "Weight limit exceeded: The elevator has triggered an alarm and shut down.";

    public WeightLimitExceededException() {
        super(DEFAULT_MESSAGE);
    }

    public WeightLimitExceededException(String message) {
        super(message);
    }
}