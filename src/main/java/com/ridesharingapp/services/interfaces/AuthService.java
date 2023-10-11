package com.ridesharingapp.services.interfaces;

import com.ridesharingapp.dto.requests.AuthenticationRequest;
import com.ridesharingapp.dto.requests.LogoutRequest;
import com.ridesharingapp.dto.requests.RegisterRequest;
import com.ridesharingapp.utils.exceptions.DuplicateDataException;

import java.util.Map;

public interface AuthService {

    Map<String, Object> login(AuthenticationRequest authRequest);

    void register(RegisterRequest registerRequest) throws DuplicateDataException;

    void loggedOut(LogoutRequest logoutRequest);
}
