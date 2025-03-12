package com.kindalab.elevatorsystem.model;

import com.kindalab.elevatorsystem.exception.KeycardInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PublicElevatorTest {

    private PublicElevator publicElevator;

    private final int VALID_FLOOR = 10;

    private final int RESTRICTED_POSITIVE_FLOOR = 50;

    private final int RESTRICTED_NEGATIVE_FLOOR = -1;

    @BeforeEach
    void setUp() {
        publicElevator = new PublicElevator(1000);
        ReflectionTestUtils.setField(publicElevator, "restrictedFloors", Arrays.asList(-1, 50));
    }

    @Test
    void testMoveToNonRestrictedFloor() throws Exception {
        publicElevator.performMoveToFloor(VALID_FLOOR);
        assertEquals(VALID_FLOOR, publicElevator.currentFloor, "Elevator should move to floor when is not restricted.");
        assertEquals(0, publicElevator.currentWeight);
        assertFalse(publicElevator.alarmActive);
    }

    @Test
    void testMoveToRestrictedFloorWithoutKeycard() {
        Exception exception = assertThrows(KeycardInvalidException.class, () -> {
            publicElevator.performMoveToFloor(RESTRICTED_POSITIVE_FLOOR);
        });

        assertTrue(exception.getMessage().contains("Access Denied"), "Exception message should indicate access denied due to missing keycard.");

    }

    @Test
    void testMoveToRestrictedFloorWithValidKeycard() throws Exception {
        Keycard validKeycard = new Keycard("testKeyCard", true);

        publicElevator.swipeKeycard(validKeycard);
        publicElevator.performMoveToFloor(RESTRICTED_NEGATIVE_FLOOR);

        assertEquals(RESTRICTED_NEGATIVE_FLOOR, publicElevator.currentFloor, "Elevator should move to restricted floor with a valid keycard");

        Object keycardField = ReflectionTestUtils.getField(publicElevator, "currentKeycard");
        assertNull(keycardField, "Keycard should be cleared after moving");
    }


    @Test
    void testClearKeycardIsCalledAfterMovingToRestrictedFloor() throws Exception {
        Keycard validKeycard = new Keycard("testKeyCard", true);
        publicElevator.swipeKeycard(validKeycard);

        publicElevator.performMoveToFloor(RESTRICTED_NEGATIVE_FLOOR);

        assertNull(ReflectionTestUtils.getField(publicElevator, "currentKeycard"), "currentKeycard is called.");
    }

    @Test
    void testSwipeKeycardInvalid() {
        Keycard invalidKeycard = new Keycard("testKeyCard", false);
        Exception exception = assertThrows(KeycardInvalidException.class, () -> {
            publicElevator.swipeKeycard(invalidKeycard);
        });
        assertTrue(exception.getMessage().contains("Invalid keycard provided"), "Exception should indicate that the keycard is invalid.");
    }

    @Test
    void testSwipeKeycardStoresValidCard() throws KeycardInvalidException {
        Keycard validKeycard = new Keycard("testKeyCard", true);
        publicElevator.swipeKeycard(validKeycard);

        Object keycardField = ReflectionTestUtils.getField(publicElevator, "currentKeycard");
        assertNotNull(keycardField, "Keycard should be saved after swipe");
    }

    @Test
    void testIsRestrictedFloor() {
        boolean restricted = (boolean) ReflectionTestUtils.invokeMethod(publicElevator, "isRestrictedFloor", RESTRICTED_POSITIVE_FLOOR);
        assertTrue(restricted, "Floor should be restricted.");

        restricted = (boolean) ReflectionTestUtils.invokeMethod(publicElevator, "isRestrictedFloor", VALID_FLOOR);
        assertFalse(restricted, "Floor  should not be restricted.");
    }
}
