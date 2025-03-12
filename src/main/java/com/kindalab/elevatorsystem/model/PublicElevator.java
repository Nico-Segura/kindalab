package com.kindalab.elevatorsystem.model;

import com.kindalab.elevatorsystem.exception.KeycardInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicElevator extends Elevator {

    public PublicElevator(@Value("${elevator.public.maxWeight}") double maxWeight) {
        super(maxWeight);
    }

    @Value("#{'${restricted.floors}'.split(',')}")
    private List<Integer> restrictedFloors;

    private Keycard currentKeycard;


    public void swipeKeycard(Keycard keycard) throws KeycardInvalidException {
        if (keycard == null) {
            throw new KeycardInvalidException("Keycard cannot be null.");
        }
        if (!keycard.isValid()) {
            throw new KeycardInvalidException("Invalid keycard provided.");
        }
        this.currentKeycard = keycard;
    }

    public Keycard getKeycard(){
        return currentKeycard;
    }

    public void clearKeycard() {
        this.currentKeycard = null;
    }

    @Override
    public void performMoveToFloor(int floor) throws Exception {
        if (isRestrictedFloor(floor)
                // Considering this validation ( isValid() ) has been done in swipeKeycard(), it is not 100% necessary but adds an extra security layer
                && (currentKeycard == null || !currentKeycard.isValid())) {
            clearKeycard();
            throw new KeycardInvalidException("A valid keycard is required to access the floor " + floor);
        }
        this.currentFloor = floor;
        if(currentKeycard != null)
            clearKeycard();
    }

    private boolean isRestrictedFloor(int floor) {
        return restrictedFloors.contains(floor);
    }

}
