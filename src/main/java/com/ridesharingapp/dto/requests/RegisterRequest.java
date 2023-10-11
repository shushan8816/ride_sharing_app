package com.ridesharingapp.dto.requests;

import com.ridesharingapp.models.Authority;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {


    @NotNull(message = "The firstName must not be null")
    private String firstName;

    @NotNull(message = "The lastName must not be null")
    private String lastName;

    @NotNull(message = "The email must not be null")
    @Email
    private String email;

    @NotNull(message = "The phoneNumber must not be null")
    private String password;

    private Authority authority;

}
