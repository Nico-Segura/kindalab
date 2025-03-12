package com.kindalab.elevatorsystem.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Building {

    private static final Logger logger = LoggerFactory.getLogger(Building.class);
    private static final int MIN_FLOOR = -1;
    private static final int MAX_FLOOR = 50;

    private final PublicElevator publicElevator;
    private final FreightElevator freightElevator;

    @Autowired
    public Building(PublicElevator publicElevator, FreightElevator freightElevator) {
        this.publicElevator = publicElevator;
        this.freightElevator = freightElevator;
    }


    public FreightElevator getFreightElevator() {
        return freightElevator;
    }

    public PublicElevator getPublicElevator() {
        return publicElevator;
    }

    public void requestElevator(boolean isPublic, int floor, Keycard keycard) throws Exception {
        if (!isValidFloor(floor)) {
            logger.error("Invalid floor: " + floor);
            throw new IllegalArgumentException("Invalid floor: " + floor);
        }

        if (isPublic) {
            if(floor == getPublicElevator().currentFloor)
                throw new IllegalArgumentException("Public elevator is already in floor: " + floor);
            requestPublicElevator(floor, keycard);
        } else {
            if(floor == getPublicElevator().currentFloor)
                throw new IllegalArgumentException("Public elevator is already in floor: " + floor);
            requestFreightElevator(floor);
        }
    }


    private void requestPublicElevator(int floor, Keycard keycard) throws Exception {
        if (keycard != null && keycard.isValid()) {
            publicElevator.swipeKeycard(keycard);
        } else {
            publicElevator.clearKeycard();
        }
        publicElevator.moveToFloor(floor);
    }


    private void requestFreightElevator(int floor) throws Exception {
        freightElevator.moveToFloor(floor);
    }

    private boolean isValidFloor(int floor) {
        return floor >= MIN_FLOOR && floor <= MAX_FLOOR;
    }

}

