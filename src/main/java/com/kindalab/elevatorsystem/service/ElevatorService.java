package com.kindalab.elevatorsystem.service;

import com.kindalab.elevatorsystem.exception.WeightLimitExceededException;
import com.kindalab.elevatorsystem.model.Keycard;

public interface ElevatorService {
    void requestElevator(int floor, Keycard keycard) throws Exception;
    void addWeight(double weight) throws WeightLimitExceededException;
    void removeWeight(double weight);
    int getCurrentFloor();
    double getCurrentWeight();
    void resetAlarm();
}