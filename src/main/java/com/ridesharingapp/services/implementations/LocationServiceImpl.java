package com.ridesharingapp.services.implementations;

import com.ridesharingapp.dto.requests.LocationUpdateRequest;
import com.ridesharingapp.models.Driver;
import com.ridesharingapp.models.Location;
import com.ridesharingapp.repositories.DriverRepository;
import com.ridesharingapp.repositories.LocationRepository;
import com.ridesharingapp.services.interfaces.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final LocationRepository locationRepository;
    private final DriverRepository driverRepository;


    @Override
    public void updateLocation(LocationUpdateRequest locationUpdateRequest) {
        Location location = locationRepository.findByRide_Id(locationUpdateRequest.getRideId());
        if (location == null) {
            location = new Location();
        }
        location.setLatitude(locationUpdateRequest.getLatitude());
        location.setLongitude(locationUpdateRequest.getLongitude());
        locationRepository.save(location);

        messagingTemplate.convertAndSend("/topic/location", location);

    }

    @Override
    public Location getDriverLocation(int driverId) {
        Driver driver = driverRepository.findDriverById(driverId);

        if (driver != null) {
            return locationRepository.findByDriver(driver);
        } else {
            throw new RuntimeException("Location not found");
        }
    }

}
