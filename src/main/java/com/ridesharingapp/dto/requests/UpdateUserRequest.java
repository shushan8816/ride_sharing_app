package com.ridesharingapp.dto.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class UpdateUserRequest {


    @Positive(message = "The userId must be greater than 0")
    private int id;

    @NotBlank(message = "The firstName must not be blank")
    private String firstName;

    @NotBlank(message = "The lastName must not be blank")
    private String lastName;

    @Email
    @NotBlank(message = "The email must not be blank")
    private String email;
}
