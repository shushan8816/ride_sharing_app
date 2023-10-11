package com.ridesharingapp.services.interfaces;

import com.ridesharingapp.enums.RideStatus;
import com.ridesharingapp.models.Driver;
import com.ridesharingapp.models.Ride;

import java.util.List;
import java.util.concurrent.Future;

public interface RideService {


    Future<Ride> createRideRequest(Ride ride);

    List<Ride> getRideRequestsByStatus(RideStatus status);

    Ride acceptRideRequest(int rideId, Driver driver);

    void cancelRideRequest(int rideId);
}
