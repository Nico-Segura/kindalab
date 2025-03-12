package com.kindalab.elevatorsystem.controller;

import com.kindalab.elevatorsystem.exception.ActivatedAlarmException;
import com.kindalab.elevatorsystem.model.Keycard;
import com.kindalab.elevatorsystem.service.PublicElevatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/elevator/public")
public class PublicElevatorController {

    private final PublicElevatorService publicElevatorService;

    @Autowired
    public PublicElevatorController(PublicElevatorService publicElevatorService) {
        this.publicElevatorService = publicElevatorService;
    }

    @Operation(summary = "Move the public elevator to a specific floor",
            description = "Moves the elevator to the specified floor. A keycard is required if the floor is restricted.")
    @PostMapping("/move")
    public ResponseEntity<String> moveElevator(
            @Parameter(description = "Floor number to move the elevator to")
            @RequestParam("floor") int floor,

            @Parameter(description = "Keycard required for restricted floors (optional)")
            @RequestBody(required = false) Keycard keycard
    ) throws Exception {
        publicElevatorService.requestElevator(floor, keycard);
        return ResponseEntity.ok("Public elevator moved to floor: " + floor);
    }

    @Operation(summary = "Add weight to the public elevator",
            description = "Adds a specified amount of weight to the elevator. Throws an exception if the weight limit is exceeded.")
    @PostMapping("/add-weight")
    public ResponseEntity<String> addWeight(
            @Parameter(description = "Amount of weight to add in kg")
            @RequestParam("weight") double weight
    ) throws Exception {
        publicElevatorService.addWeight(weight);
        return ResponseEntity.ok("Weight added successfully.");
    }

    @Operation(summary = "Remove weight from the public elevator",
            description = "Removes a specified amount of weight from the elevator.")
    @PostMapping("/remove-weight")
    public ResponseEntity<String> removeWeight(
            @Parameter(description = "Amount of weight to remove in kg")
            @RequestParam("weight") double weight
    ) {
        publicElevatorService.removeWeight(weight);
        return ResponseEntity.ok("Weight removed successfully.");
    }

    @Operation(summary = "Get the current floor of the public elevator",
            description = "Returns the floor number where the public elevator is currently located.")
    @GetMapping("/current-floor")
    public ResponseEntity<String> getCurrentFloor() {
        int floor = publicElevatorService.getCurrentFloor();
        return ResponseEntity.ok("Public elevator is at floor: " + floor);
    }

    @Operation(summary = "Get the current weight of the public elevator",
            description = "Returns the total weight currently inside the public elevator.")
    @GetMapping("/current-weight")
    public ResponseEntity<String> getCurrentWeight() {
        double weight = publicElevatorService.getCurrentWeight();
        return ResponseEntity.ok("Public elevator current weight: " + weight + " kg");
    }

    @Operation(summary = "Reset the alarm of the public elevator",
            description = "Resets the alarm if it was triggered due to excessive weight.")
    @PostMapping("/reset-alarm")
    public ResponseEntity<String> resetAlarm() throws ActivatedAlarmException {
        try {
            publicElevatorService.resetAlarm();
            return ResponseEntity.ok("Public elevator alarm reset successfully.");
        } catch (Exception e) {
            throw new ActivatedAlarmException();
        }
    }
}
