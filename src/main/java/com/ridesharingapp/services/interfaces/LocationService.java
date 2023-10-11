package com.ridesharingapp.services.interfaces;

import com.ridesharingapp.dto.requests.LocationUpdateRequest;
import com.ridesharingapp.models.Location;

public interface LocationService {
    void updateLocation(LocationUpdateRequest locationUpdateRequest);
    Location getDriverLocation(int driverId);

}
