package com.kindalab.elevatorsystem.model;

import com.kindalab.elevatorsystem.exception.KeycardInvalidException;
import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BuildingTest {

    @Mock
    private PublicElevator publicElevator;

    @Mock
    private FreightElevator freightElevator;

    @InjectMocks
    private Building building;

    private final int VALID_FLOOR = 10;

    private Keycard createValidKeyCard() {
        return new Keycard("validTestKey1", true);
    }

    private Keycard createInvalidKeyCard() {
        return new Keycard("invalidTestKey1", false);
    }

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(publicElevator, "restrictedFloors", Arrays.asList(-1, 50));  // ✅ Asegurar que tenga valores
    }


    @Test
    public void testRequestElevatorForInvalidFloor() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> building.requestElevator(true, 55, null),
                "Should throw IllegalArgumentException for an invalid floor"
        );

        assertTrue(exception.getMessage().contains("Invalid floor"));
    }



    @Test
    public void testRequestPublicElevatorClearsCardWithInvalidCard() throws Exception {
        ReflectionTestUtils.setField(publicElevator, "restrictedFloors", Arrays.asList(-1, 50));
        Mockito.doNothing().when(publicElevator).validateAlarm();
        Mockito.doCallRealMethod().when(publicElevator).moveToFloor(-1);
        Mockito.doCallRealMethod().when(publicElevator).performMoveToFloor(-1);

        assertThrows(KeycardInvalidException.class, () -> {
            building.requestElevator(true, -1, createInvalidKeyCard());
        });

        Mockito.verify(publicElevator, Mockito.times(2)).clearKeycard();
        assertEquals(0, publicElevator.currentFloor, "Public elevator should not move if is a restricted floor with an invalid keycard");
        assertNull(publicElevator.getKeycard(), "Keycard should be cleared if is a restricted floor with an invalid keycard");
    }





    @Test
    void testRequestPublicElevatorSuccess() throws Exception {
        Keycard keycard = createValidKeyCard();

        Mockito.doNothing().when(publicElevator).swipeKeycard(keycard);
        Mockito.doNothing().when(publicElevator).moveToFloor(VALID_FLOOR);

        building.requestElevator(true, VALID_FLOOR, keycard);

        Mockito.verify(publicElevator, Mockito.times(1)).swipeKeycard(keycard);
        Mockito.verify(publicElevator, Mockito.times(1)).moveToFloor(VALID_FLOOR);
        Mockito.verify(publicElevator, Mockito.times(0)).clearKeycard();
    }

    @Test
    void testRequestPublicElevatorFailureDueToException() throws Exception {
        Keycard keycard = createValidKeyCard();

        Mockito.doNothing().when(publicElevator).swipeKeycard(keycard);
        Mockito.doThrow(new KeycardInvalidException()).when(publicElevator).moveToFloor(VALID_FLOOR);

        assertThrows(KeycardInvalidException.class, () -> {
            building.requestElevator(true, VALID_FLOOR, keycard);
        });
    }

    @Test
    void testRequestFreightElevatorSuccess() throws Exception {
        Mockito.doNothing().when(freightElevator).moveToFloor(VALID_FLOOR);

        building.requestElevator(false, VALID_FLOOR, null);

        Mockito.verify(freightElevator, Mockito.times(1)).moveToFloor(VALID_FLOOR);
    }

    @Test
    void testRequestFreightElevatorFailureDueToException() throws Exception {
        Mockito.doThrow(new WeightLimitExceededException("Elevator system is shut down"))
                .when(freightElevator).moveToFloor(VALID_FLOOR);

        assertThrows(WeightLimitExceededException.class, () -> {
            building.requestElevator(false, VALID_FLOOR, null);
        });
    }

    @Test
    public void testIsValidFloor() {
        int validFloorZero = 0;
        int validFloorOne = 1;
        int invalidPositiveFloor = 100000;
        int invalidNegativeFloor = -100000;

        Boolean isValid1 = (Boolean) ReflectionTestUtils.invokeMethod(building, "isValidFloor", validFloorZero);
        Boolean isValid2 = (Boolean) ReflectionTestUtils.invokeMethod(building, "isValidFloor", validFloorOne);
        Boolean isValid3 = (Boolean) ReflectionTestUtils.invokeMethod(building, "isValidFloor", invalidPositiveFloor);
        Boolean isValid4 = (Boolean) ReflectionTestUtils.invokeMethod(building, "isValidFloor", invalidNegativeFloor);

        assertTrue(isValid1, String.format("Floor %d should be valid", validFloorZero));
        assertTrue(isValid2, String.format("Floor %d should be valid", validFloorOne));
        assertFalse(isValid3, String.format("Floor %d should be invalid", invalidPositiveFloor));
        assertFalse(isValid4, String.format("Floor %d should be invalid", invalidNegativeFloor));
    }
}
