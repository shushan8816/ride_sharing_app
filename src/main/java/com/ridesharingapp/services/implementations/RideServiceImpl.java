package com.ridesharingapp.services.implementations;

import com.ridesharingapp.enums.RideStatus;
import com.ridesharingapp.models.Driver;
import com.ridesharingapp.models.Ride;
import com.ridesharingapp.repositories.DriverRepository;
import com.ridesharingapp.repositories.RideRepository;
import com.ridesharingapp.services.interfaces.RideService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Log4j2
@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final DriverRepository driverRepository;

    private final RideRepository rideRepository;

    @Async
    @Override
    @Transactional
    public Future<Ride> createRideRequest(Ride ride) {
        if (ride == null || ride.getStartLocation() == null || ride.getEndLocation() == null) {
            throw new IllegalArgumentException("Invalid ride request");
        }

        Driver driver = driverRepository.findDriverById(ride.getDriver().getId());

        if (driver == null) {
            throw new RuntimeException("No available drivers at the moment. Please try again later.");
        }


        Ride newRide = new Ride();
        newRide.setDriver(ride.getDriver());
        newRide.setStartTime(ride.getStartTime());
        newRide.setEndTime(ride.getEndTime());
        newRide.setStartLocation(ride.getStartLocation());
        newRide.setEndLocation(ride.getEndLocation());
        newRide.setRideStatus(RideStatus.REQUESTED);

        log.info("Save the ride record to the database");

        return AsyncResult.forValue(rideRepository.save(newRide));
    }

    @Override
    public List<Ride> getRideRequestsByStatus(RideStatus status) {
        return rideRepository.findByRideStatus(status);

    }

    @Override
    @Transactional
    public Ride acceptRideRequest(int rideId, Driver driver) {
        Optional<Ride> optionalRide = rideRepository.findById(rideId);
        if (optionalRide.isPresent()) {
            Ride ride = optionalRide.get();
            if (ride.getRideStatus() ==RideStatus.REQUESTED) {
                ride.setDriver(driver);
                ride.setRideStatus(RideStatus.ACCEPTED);
                return rideRepository.save(ride);
            }
        }
        throw new RuntimeException("Unable to accept the ride request");
    }

    @Override
    @Transactional
    public void cancelRideRequest(int rideId) {
        Optional<Ride> optionalRide = rideRepository.findById(rideId);

        if (optionalRide.isEmpty()) {
            throw new RuntimeException("Ride not found for rideId: " + rideId);
        }

        Ride ride = optionalRide.get();
        ride.setRideStatus(RideStatus.CANCELED);

        log.info("Save the updated ride to the database");

        rideRepository.save(ride);
    }
}
