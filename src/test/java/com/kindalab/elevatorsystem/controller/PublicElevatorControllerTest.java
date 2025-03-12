package com.kindalab.elevatorsystem.controller;

import com.kindalab.elevatorsystem.exception.ActivatedAlarmException;
import com.kindalab.elevatorsystem.model.Keycard;
import com.kindalab.elevatorsystem.service.PublicElevatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicElevatorControllerTest {

    @Mock
    private PublicElevatorService publicElevatorService;

    @InjectMocks
    private PublicElevatorController publicElevatorController;

    @Test
    void testMoveElevatorSuccess() throws Exception {
        int floor = 5;
        Keycard keycard = new Keycard("valid-key", true);

        ResponseEntity<String> response = publicElevatorController.moveElevator(floor, keycard);

        verify(publicElevatorService, times(1)).requestElevator(floor, keycard);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Public elevator moved to floor: " + floor, response.getBody());
    }

    @Test
    void testAddWeightSuccess() throws Exception {
        double weight = 500.0;

        ResponseEntity<String> response = publicElevatorController.addWeight(weight);

        verify(publicElevatorService, times(1)).addWeight(weight);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Weight added successfully.", response.getBody());
    }

    @Test
    void testRemoveWeight() {
        double weight = 300.0;

        ResponseEntity<String> response = publicElevatorController.removeWeight(weight);

        verify(publicElevatorService, times(1)).removeWeight(weight);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Weight removed successfully.", response.getBody());
    }

    @Test
    void testGetCurrentFloor() {
        int expectedFloor = 3;
        when(publicElevatorService.getCurrentFloor()).thenReturn(expectedFloor);

        ResponseEntity<String> response = publicElevatorController.getCurrentFloor();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Public elevator is at floor: " + expectedFloor, response.getBody());
    }

    @Test
    void testGetCurrentWeight() {
        double expectedWeight = 450.0;
        when(publicElevatorService.getCurrentWeight()).thenReturn(expectedWeight);

        ResponseEntity<String> response = publicElevatorController.getCurrentWeight();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Public elevator current weight: " + expectedWeight + " kg", response.getBody());
    }

    @Test
    void testResetAlarm() throws ActivatedAlarmException {
        ResponseEntity<String> response = publicElevatorController.resetAlarm();

        verify(publicElevatorService, times(1)).resetAlarm();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Public elevator alarm reset successfully.", response.getBody());
    }
}
