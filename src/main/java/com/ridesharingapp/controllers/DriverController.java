package com.ridesharingapp.controllers;

import com.ridesharingapp.enums.DriverStatus;
import com.ridesharingapp.models.Driver;
import com.ridesharingapp.services.interfaces.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;


    @GetMapping("/availability")
    public ResponseEntity<List<Driver>> getAvailableDrivers() {
        return ResponseEntity.ok(driverService.findAvailableDrivers());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateDriverStatus(@PathVariable int id, @RequestParam DriverStatus newStatus) {
        driverService.updateDriverStatus(id, newStatus);
        return  ResponseEntity.ok().build();
    }
}