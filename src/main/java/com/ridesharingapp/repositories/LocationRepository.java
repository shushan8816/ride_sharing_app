package com.ridesharingapp.repositories;

import com.ridesharingapp.models.Driver;
import com.ridesharingapp.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Location findByRide_Id( int rideId);
    Location findByDriver(Driver driver);
}
