package com.ridesharingapp.repositories;

import com.ridesharingapp.enums.RideStatus;
import com.ridesharingapp.models.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepository extends JpaRepository<Ride, Integer> {
    List<Ride> findByRideStatus(RideStatus status);

}
