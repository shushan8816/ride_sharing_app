package com.ridesharingapp.services.implementations;

import com.ridesharingapp.enums.DriverStatus;
import com.ridesharingapp.models.Driver;
import com.ridesharingapp.repositories.DriverRepository;
import com.ridesharingapp.services.interfaces.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;


    @Override
    public List<Driver> findAvailableDrivers() {
        return driverRepository.findByDriverStatus(DriverStatus.AVAILABLE);
    }

    @Override
    @Transactional
    public void updateDriverStatus(int driverId, DriverStatus newStatus) {
        Optional<Driver> optionalDriver = driverRepository.findById(driverId);
        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            driver.setDriverStatus(newStatus);
            driverRepository.save(driver);
        } else {
            throw new RuntimeException("Driver not found");
        }
    }

}
