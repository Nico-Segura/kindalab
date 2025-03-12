package com.kindalab.elevatorsystem.model;

import com.kindalab.elevatorsystem.exception.ActivatedAlarmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FreightElevator extends Elevator {
    public FreightElevator(@Value("${elevator.freight.maxWeight}") double maxWeight) {
        super(maxWeight);
    }

    @Override
    public void performMoveToFloor(int floor) throws ActivatedAlarmException {
        validateAlarm();
        this.currentFloor = floor;
    }
}