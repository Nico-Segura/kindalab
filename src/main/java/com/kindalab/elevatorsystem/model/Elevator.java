package com.kindalab.elevatorsystem.model;

import com.kindalab.elevatorsystem.exception.ActivatedAlarmException;
import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public abstract class Elevator {
    private static final Logger logger = LoggerFactory.getLogger(Elevator.class);

    protected int currentFloor;
    protected double maxWeight;
    protected double currentWeight;
    protected boolean alarmActive;


    public Elevator(double maxWeight) {
        this.currentFloor = 0;
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
        this.alarmActive = false;
    }

    public final void moveToFloor(int floor) throws Exception {
        validateAlarm();
        performMoveToFloor(floor);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    protected abstract void performMoveToFloor(int floor) throws Exception;


    public void addWeight(double weight) throws WeightLimitExceededException {
        if ((currentWeight + weight) > maxWeight) {
            triggerWeightAlarm();
            throw new WeightLimitExceededException();
        }
        currentWeight += weight;
    }


    public void removeWeight(double weight) {
        currentWeight = Math.max(0, currentWeight - weight);
    }

    private void triggerWeightAlarm() {
        logger.error("ALARM! Weight limit exceeded.");
        alarmActive = true;
    }

    public void resetAlarm() {
        alarmActive = false;
    }

    protected void validateAlarm() throws ActivatedAlarmException {
        if (alarmActive) {
            throw new ActivatedAlarmException("Weight limit exceeded, reset alarm to restore elevator.");
        }
    }
}
