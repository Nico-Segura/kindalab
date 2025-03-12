package com.kindalab.elevatorsystem.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleKeycardInvalidException() {
        KeycardInvalidException exception = new KeycardInvalidException("Invalid keycard");
        ResponseEntity<String> response = exceptionHandler.handleKeycardException(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid keycard"));
        assertTrue(response.getBody().contains("Access Denied:"));

    }

    @Test
    void testHandleWeightLimitExceededException() {
        WeightLimitExceededException exception = new WeightLimitExceededException("Weight limit exceeded");
        ResponseEntity<String> response = exceptionHandler.handleWeightLimitException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Weight limit exceeded", response.getBody());

        exception = new WeightLimitExceededException();
        response = exceptionHandler.handleWeightLimitException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Weight limit exceeded: The elevator has triggered an alarm and shut down.", response.getBody());
    }

    @Test
    void testHandleActivatedAlarmException() {
        ActivatedAlarmException exception = new ActivatedAlarmException("Alarm active");
        ResponseEntity<String> response = exceptionHandler.handleActivatedAlarmException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("ACTIVED ALARM!"));
    }

    @Test
    void testHandleIllegalArgumentException() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid floor");
        ResponseEntity<String> response = exceptionHandler.handleIllegalArgumentException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid floor", response.getBody());
    }

    @Test
    void testHandleGeneralException() {
        Exception exception = new Exception("Unexpected error");
        ResponseEntity<String> response = exceptionHandler.handleGeneralException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: Unexpected error", response.getBody());
    }
}
