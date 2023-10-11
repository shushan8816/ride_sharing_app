package com.ridesharingapp.dto.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AuthenticationRequest {

    @NotBlank(message = "The email must not be blank")
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank(message = "The password must not be blank")
    @Size(min = 6, max = 20)
    private String password;

}
