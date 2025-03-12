package com.kindalab.elevatorsystem.controller;

import com.kindalab.elevatorsystem.exception.ActivatedAlarmException;
import com.kindalab.elevatorsystem.service.FreightElevatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elevator/freight")
public class FreightElevatorController {

    private final FreightElevatorService freightElevatorService;

    @Autowired
    public FreightElevatorController(FreightElevatorService freightElevatorService) {
        this.freightElevatorService = freightElevatorService;
    }

    @Operation(summary = "Move the freight elevator to a specific floor",
            description = "Moves the freight elevator to the requested floor.")
    @PostMapping("/move")
    public ResponseEntity<String> moveElevator(
            @Parameter(description = "Floor number to move the elevator to")
            @RequestParam("floor") int floor
    ) throws Exception {
        freightElevatorService.requestElevator(floor, null);
        return ResponseEntity.ok("Freight elevator moved to floor: " + floor);
    }

    @Operation(summary = "Add weight to the freight elevator",
            description = "Adds a specified amount of weight to the freight elevator. Throws an exception if the weight limit is exceeded.")
    @PostMapping("/add-weight")
    public ResponseEntity<String> addWeight(
            @Parameter(description = "Amount of weight to add in kg")
            @RequestParam("weight") double weight
    ) throws Exception {
        freightElevatorService.addWeight(weight);
        return ResponseEntity.ok("Weight added successfully.");
    }

    @Operation(summary = "Remove weight from the freight elevator",
            description = "Removes a specified amount of weight from the freight elevator.")
    @PostMapping("/remove-weight")
    public ResponseEntity<String> removeWeight(
            @Parameter(description = "Amount of weight to remove in kg")
            @RequestParam("weight") double weight
    ) {
        freightElevatorService.removeWeight(weight);
        return ResponseEntity.ok("Weight removed successfully");
    }

    @Operation(summary = "Get the current floor of the freight elevator",
            description = "Returns the floor number where the freight elevator is currently located")
    @GetMapping("/current-floor")
    public ResponseEntity<String> getCurrentFloor() {
        int floor = freightElevatorService.getCurrentFloor();
        return ResponseEntity.ok("Freight elevator is at floor: " + floor);
    }

    @Operation(summary = "Get the current weight of the freight elevator",
            description = "Returns the total weight currently inside the freight elevator.")
    @GetMapping("/current-weight")
    public ResponseEntity<String> getCurrentWeight() {
        double weight = freightElevatorService.getCurrentWeight();
        return ResponseEntity.ok("Freight elevator current weight: " + weight + " kg");
    }

    @Operation(summary = "Reset the alarm of the freight elevator",
            description = "Resets the alarm if it was triggered due to excessive weight.")
    @PostMapping("/reset-alarm")
    public ResponseEntity<String> resetAlarm() throws ActivatedAlarmException {
        try {
            freightElevatorService.resetAlarm();
            return ResponseEntity.ok("Freight elevator alarm reset successfully.");
        } catch (Exception e) {
            throw new ActivatedAlarmException();
        }
    }
}
