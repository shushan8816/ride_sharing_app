package com.ridesharingapp.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class LocationUpdateRequest {

    @Positive(message = "ID must be greater than 0")
    private int rideId;

    @NotBlank(message = "The latitude must not be blank")
    private double latitude;

    @NotBlank(message = "The longitude must not be blank")
    private double longitude;

}
