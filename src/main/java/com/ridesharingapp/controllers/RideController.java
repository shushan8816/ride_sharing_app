package com.ridesharingapp.controllers;

import com.ridesharingapp.enums.RideStatus;
import com.ridesharingapp.models.Driver;
import com.ridesharingapp.models.Ride;
import com.ridesharingapp.services.interfaces.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Future;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ride")
public class RideController {

    private final RideService rideService;


    @GetMapping("/allRequests")
    public ResponseEntity<List<Ride>> getRideRequests() {
        return ResponseEntity.ok(rideService.getRideRequestsByStatus(RideStatus.REQUESTED));
    }

    @PostMapping("/request")
    public ResponseEntity<Future<Ride>> createRideRequest(@RequestBody Ride ride) {
        return ResponseEntity.ok(rideService.createRideRequest(ride));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<Ride> acceptRideRequest(@PathVariable int id, @RequestBody Driver driver) {
        return ResponseEntity.ok(rideService.acceptRideRequest(id, driver));
    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelRide(@PathVariable int id) {
        rideService.cancelRideRequest(id);
        return ResponseEntity.ok().build();
    }
}
