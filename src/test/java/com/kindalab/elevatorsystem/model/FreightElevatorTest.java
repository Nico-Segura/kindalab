package com.kindalab.elevatorsystem.model;

import com.kindalab.elevatorsystem.exception.ActivatedAlarmException;
import com.kindalab.elevatorsystem.exception.KeycardInvalidException;
import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FreightElevatorTest {

    private FreightElevator freightElevator;

    private final double MAX_WEIGHT = 3000.00;

    private final int VALID_FLOOR = 5;

    @BeforeEach
    void setUp() {
        freightElevator = new FreightElevator(MAX_WEIGHT);
    }

    @Test
    void testMoveToFloorSuccess() throws Exception {
        freightElevator.moveToFloor(VALID_FLOOR);
        assertEquals(VALID_FLOOR, freightElevator.currentFloor, "Freight elevator should move to valid floor");
    }

    @Test
    void testMoveToFloorWhenAlarmActive() throws Exception {
        freightElevator.alarmActive = true;

        ActivatedAlarmException exception = assertThrows(
                ActivatedAlarmException.class,
                () -> freightElevator.moveToFloor(VALID_FLOOR));

        assertTrue(exception.getMessage().contains("Elevator system is shut down"));
        assertEquals(0, freightElevator.currentFloor, "Freight elevator should not move when alarm is active");
    }

    @Test
    void testMoveToRestrictedFloor() throws Exception {
        freightElevator.moveToFloor(-1);
        assertEquals(-1, freightElevator.currentFloor, "Freight elevator should move to all floors, restricted ones also");

        freightElevator.moveToFloor(50);
        assertEquals(50, freightElevator.currentFloor, "Freight elevator should move to restricted top floor");
    }

    @Test
    void testAddWeightWithinLimit() throws Exception {
        freightElevator.addWeight(MAX_WEIGHT);
        assertEquals(MAX_WEIGHT, freightElevator.currentWeight, "Freight elevator weight should be updated (using elevator's max weight).");
    }

    @Test
    void testAddWeightExceedLimit() {
        WeightLimitExceededException exception = assertThrows(
                WeightLimitExceededException.class,
                () -> freightElevator.addWeight(MAX_WEIGHT + 1 ),
                "Adding weight beyond the limit should throw WeightLimitExceededException."
        );

        assertTrue(exception.getMessage().contains("shut down"));
        assertTrue(freightElevator.alarmActive, "Alarm should be active when weight limit is exceeded.");
        assertEquals(0, freightElevator.currentWeight);
    }

    @Test
    void testAddWeightDoesNotActivateAlarmWithinLimit() throws Exception {
        freightElevator.addWeight(MAX_WEIGHT - 1);
        assertFalse(freightElevator.alarmActive, "Alarm should not be active if weight is within limit");
    }

    @Test
    void testRemoveWeight() throws Exception {
        double weightToRemove = 2.55;
        freightElevator.addWeight(MAX_WEIGHT);
        freightElevator.removeWeight(weightToRemove);
        assertEquals(MAX_WEIGHT - weightToRemove, freightElevator.currentWeight);
    }

    @Test
    void testRemoveWeightDoesNotGoBelowZero() throws Exception {
        freightElevator.addWeight(100);
        freightElevator.removeWeight(200);

        assertEquals(0, freightElevator.currentWeight, "Weight should not go below 0");
    }

}