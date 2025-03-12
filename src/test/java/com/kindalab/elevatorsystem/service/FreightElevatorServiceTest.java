package com.kindalab.elevatorsystem.service;

import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import com.kindalab.elevatorsystem.model.Building;
import com.kindalab.elevatorsystem.model.FreightElevator;
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
class FreightElevatorServiceTest {

    @Mock
    private Building building;

    @Mock
    private FreightElevator freightElevator;

    @InjectMocks
    private FreightElevatorService freightElevatorService;

    private final int VALID_FLOOR = 5;
    private final double MAX_WEIGHT = 3000.00;

    @BeforeEach
    void setUp() {
        lenient().when(building.getFreightElevator()).thenReturn(freightElevator);
    }

    @Test
    void testRequestElevatorSuccess() throws Exception {
        Mockito.doNothing().when(building).requestElevator(false, VALID_FLOOR, null);

        freightElevatorService.requestElevator(VALID_FLOOR, null);

        Mockito.verify(building, Mockito.times(1)).requestElevator(false, VALID_FLOOR, null);
    }

    @Test
    void testRequestElevatorFailure() throws Exception {
        Mockito.doThrow(new WeightLimitExceededException("Elevator system is shut down"))
                .when(building).requestElevator(false, VALID_FLOOR, null);

        assertThrows(WeightLimitExceededException.class, () -> {
            freightElevatorService.requestElevator(VALID_FLOOR, null);
        });

        Mockito.verify(building, Mockito.times(1)).requestElevator(false, VALID_FLOOR, null);
    }

    @Test
    void testAddWeightSuccess() throws Exception {
        Mockito.doNothing().when(freightElevator).addWeight(MAX_WEIGHT);

        freightElevatorService.addWeight(MAX_WEIGHT);

        Mockito.verify(freightElevator, Mockito.times(1)).addWeight(MAX_WEIGHT);
    }

    @Test
    void testAddWeightFailure() throws Exception {
        double exceededWeight = MAX_WEIGHT + 1;
        Mockito.doThrow(new WeightLimitExceededException("Weight limit exceeded")).when(freightElevator).addWeight(exceededWeight);

        assertThrows(WeightLimitExceededException.class, () -> {
            freightElevatorService.addWeight(exceededWeight);
        });

        Mockito.verify(freightElevator, Mockito.times(1)).addWeight(exceededWeight);
    }

    @Test
    void testRemoveWeight() {
        freightElevatorService.removeWeight(MAX_WEIGHT);
        Mockito.verify(freightElevator, Mockito.times(1)).removeWeight(MAX_WEIGHT);
    }

    @Test
    void testGetCurrentFloor() {
        Mockito.when(freightElevator.getCurrentFloor()).thenReturn(VALID_FLOOR);

        int currentFloor = freightElevatorService.getCurrentFloor();
        assertEquals(VALID_FLOOR, currentFloor, "Freight elevator should be at floor " + VALID_FLOOR);
        Mockito.verify(freightElevator, Mockito.times(1)).getCurrentFloor();
    }
}
