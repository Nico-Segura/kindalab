package com.kindalab.elevatorsystem.service;

import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import com.kindalab.elevatorsystem.model.Building;
import com.kindalab.elevatorsystem.model.Keycard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FreightElevatorService implements ElevatorService {

    private final Building building;

    @Autowired
    public FreightElevatorService(Building building) {
        this.building = building;
    }

    @Override
    public void requestElevator(int floor, Keycard keycard) throws Exception {
        building.requestElevator(false, floor, null);
    }

    @Override
    public void addWeight(double weight) throws WeightLimitExceededException {
        building.getFreightElevator().addWeight(weight);
    }

    @Override
    public void removeWeight(double weight) {
        building.getFreightElevator().removeWeight(weight);
    }

    @Override
    public int getCurrentFloor() {
        return building.getFreightElevator().getCurrentFloor();
    }

    @Override
    public double getCurrentWeight() {
        return building.getFreightElevator().getCurrentWeight();
    }

    @Override
    public void resetAlarm() {
        building.getFreightElevator().resetAlarm();
    }
}
