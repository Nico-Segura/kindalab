package com.kindalab.elevatorsystem.service;

import com.kindalab.elevatorsystem.exception.KeycardInvalidException;
import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import com.kindalab.elevatorsystem.model.Building;
import com.kindalab.elevatorsystem.model.Keycard;
import com.kindalab.elevatorsystem.model.PublicElevator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class PublicElevatorServiceTest {

    @Mock
    private Building building;

    @Mock
    private PublicElevator publicElevator;

    @InjectMocks
    private PublicElevatorService publicElevatorService;

    private final int VALID_FLOOR = 5;
    private final int RESTRICTED_FLOOR = 50;
    private final double MAX_WEIGHT = 1000.00;

    @BeforeEach
    void setUp() {
        lenient().when(building.getPublicElevator()).thenReturn(publicElevator);
    }

    @Test
    void testRequestElevatorSuccess() throws Exception {
        Mockito.doNothing().when(building).requestElevator(true, VALID_FLOOR, null);

        publicElevatorService.requestElevator(VALID_FLOOR, null);

        Mockito.verify(building, Mockito.times(1)).requestElevator(true, VALID_FLOOR, null);
    }

    @Test
    void testRequestElevatorFailure() throws Exception {
        Mockito.doThrow(new KeycardInvalidException("Access Denied")).when(building).requestElevator(true, VALID_FLOOR, null);

        assertThrows(KeycardInvalidException.class, () -> {
            publicElevatorService.requestElevator(VALID_FLOOR, null);
        });

        Mockito.verify(building, Mockito.times(1)).requestElevator(true, VALID_FLOOR, null);
    }

    @Test
    void testRequestElevatorRestrictedFloorWithValidKeycard() throws Exception {
        Keycard validKeycard = new Keycard("validKey1", true);
        Mockito.doNothing().when(building).requestElevator(true, RESTRICTED_FLOOR, validKeycard);

        publicElevatorService.requestElevator(RESTRICTED_FLOOR, validKeycard);

        Mockito.verify(building).requestElevator(true, RESTRICTED_FLOOR, validKeycard);
    }

    @Test
    void testRequestElevatorRestrictedFloorWithInvalidKeycard() throws Exception {
        Keycard invalidKeycard = new Keycard("invalidKey2", false);
        Mockito.doThrow(new KeycardInvalidException("Access Denied")).when(building).requestElevator(true, RESTRICTED_FLOOR, invalidKeycard);

        assertThrows(KeycardInvalidException.class, () -> {
            publicElevatorService.requestElevator(RESTRICTED_FLOOR, invalidKeycard);
        });

        Mockito.verify(building).requestElevator(true, RESTRICTED_FLOOR, invalidKeycard);
    }

    @Test
    void testAddWeightSuccess() throws Exception {
        Mockito.doNothing().when(publicElevator).addWeight(MAX_WEIGHT);

        publicElevatorService.addWeight(MAX_WEIGHT);

        Mockito.verify(publicElevator, Mockito.times(1)).addWeight(MAX_WEIGHT);
    }

    @Test
    void testAddWeightFailure() throws Exception {
        double exceededWeight = MAX_WEIGHT + 1;
        Mockito.doThrow(new WeightLimitExceededException("Weight limit exceeded")).when(publicElevator).addWeight(exceededWeight);

        assertThrows(WeightLimitExceededException.class, () -> {
            publicElevatorService.addWeight(exceededWeight);
        });

        Mockito.verify(publicElevator, Mockito.times(1)).addWeight(exceededWeight);
    }

    @Test
    void testRemoveWeight() {
        publicElevatorService.removeWeight(MAX_WEIGHT);
        Mockito.verify(publicElevator, Mockito.times(1)).removeWeight(MAX_WEIGHT);
    }

    @Test
    void testGetCurrentFloor() {
        Mockito.when(publicElevator.getCurrentFloor()).thenReturn(VALID_FLOOR);

        int currentFloor = publicElevatorService.getCurrentFloor();
        assertEquals(VALID_FLOOR, currentFloor, "Public elevator should be at floor " + VALID_FLOOR);
        Mockito.verify(publicElevator, Mockito.times(1)).getCurrentFloor();
    }
}
