package com.kindalab.elevatorsystem.controller;

import com.kindalab.elevatorsystem.exception.ActivatedAlarmException;
import com.kindalab.elevatorsystem.service.FreightElevatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FreightElevatorControllerTest {

    @Mock
    private FreightElevatorService freightElevatorService;

    @InjectMocks
    private FreightElevatorController freightElevatorController;

    @Test
    void testMoveElevatorSuccess() throws Exception {
        int floor = 7;

        ResponseEntity<String> response = freightElevatorController.moveElevator(floor);

        verify(freightElevatorService, times(1)).requestElevator(floor, null);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Freight elevator moved to floor: " + floor, response.getBody());
    }

    @Test
    void testAddWeightSuccess() throws Exception {
        double weight = 1200.0;

        ResponseEntity<String> response = freightElevatorController.addWeight(weight);

        verify(freightElevatorService, times(1)).addWeight(weight);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Weight added successfully.", response.getBody());
    }

    @Test
    void testRemoveWeight() {
        double weight = 400.0;

        ResponseEntity<String> response = freightElevatorController.removeWeight(weight);

        verify(freightElevatorService, times(1)).removeWeight(weight);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Weight removed successfully.", response.getBody());
    }

    @Test
    void testGetCurrentFloor() {
        int expectedFloor = 10;
        when(freightElevatorService.getCurrentFloor()).thenReturn(expectedFloor);

        ResponseEntity<String> response = freightElevatorController.getCurrentFloor();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Freight elevator is at floor: " + expectedFloor, response.getBody());
    }

    @Test
    void testGetCurrentWeight() {
        double expectedWeight = 1500.0;
        when(freightElevatorService.getCurrentWeight()).thenReturn(expectedWeight);

        ResponseEntity<String> response = freightElevatorController.getCurrentWeight();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Freight elevator current weight: " + expectedWeight + " kg", response.getBody());
    }

    @Test
    void testResetAlarm() throws ActivatedAlarmException {
        ResponseEntity<String> response = freightElevatorController.resetAlarm();

        verify(freightElevatorService, times(1)).resetAlarm();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Freight elevator alarm reset successfully.", response.getBody());
    }
}
