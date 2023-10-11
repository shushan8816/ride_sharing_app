package com.ridesharingapp.repositories;

import com.ridesharingapp.enums.DriverStatus;
import com.ridesharingapp.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

    List<Driver> findByDriverStatus(DriverStatus status);
    Driver findDriverById(int driverId);

}
