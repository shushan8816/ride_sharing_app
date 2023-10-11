package com.ridesharingapp.services.interfaces;

import com.ridesharingapp.enums.DriverStatus;
import com.ridesharingapp.models.Driver;

import java.util.List;

public interface DriverService {

    List<Driver> findAvailableDrivers();

    void updateDriverStatus(int driverId, DriverStatus newStatus);

}
