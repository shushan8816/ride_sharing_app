package com.ridesharingapp.controllers;

import com.ridesharingapp.dto.requests.LocationUpdateRequest;
import com.ridesharingapp.models.Location;
import com.ridesharingapp.services.interfaces.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @MessageMapping("/updateLocation")
    @SendTo("/topic/locationUpdate")
    public ResponseEntity<Void> updateUserLocation(@RequestBody LocationUpdateRequest locationUpdateRequest) {
        locationService.updateLocation(locationUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{driverId}")
    public ResponseEntity<Location> getDriverLocation(@PathVariable int driverId){
        return  ResponseEntity.ok(locationService.getDriverLocation(driverId));
    }
}