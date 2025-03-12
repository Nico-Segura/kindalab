package com.kindalab.elevatorsystem.service;

import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import com.kindalab.elevatorsystem.model.Building;
import com.kindalab.elevatorsystem.model.Keycard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicElevatorService implements ElevatorService {

    private final Building building;

    @Autowired
    public PublicElevatorService(Building building) {
        this.building = building;
    }

    @Override
    public void requestElevator(int floor, Keycard keycard) throws Exception {
        building.requestElevator(true, floor, keycard);
    }

    @Override
    public void addWeight(double weight) throws WeightLimitExceededException {
        building.getPublicElevator().addWeight(weight);
    }

    @Override
    public void removeWeight(double weight) {
        building.getPublicElevator().removeWeight(weight);
    }

    @Override
    public int getCurrentFloor() {
        return building.getPublicElevator().getCurrentFloor();
    }

    @Override
    public double getCurrentWeight() {
        return building.getPublicElevator().getCurrentWeight();
    }

    @Override
    public void resetAlarm() {
        building.getPublicElevator().resetAlarm();
    }
}
