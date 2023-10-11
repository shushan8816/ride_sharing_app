package com.ridesharingapp.services.implementations;

import com.ridesharingapp.dto.requests.AuthenticationRequest;
import com.ridesharingapp.dto.requests.LogoutRequest;
import com.ridesharingapp.dto.requests.RegisterRequest;
import com.ridesharingapp.models.User;
import com.ridesharingapp.repositories.UserRepository;
import com.ridesharingapp.security.JwtTokenProvider;
import com.ridesharingapp.services.interfaces.AuthService;
import com.ridesharingapp.utils.exceptions.DuplicateDataException;
import com.ridesharingapp.utils.exceptions.JwtAuthenticationException;
import com.ridesharingapp.utils.helper.JwtTokenCache;
import com.ridesharingapp.utils.helper.ResponseMassage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenCache jwtTokenCache;

    private final ResponseMassage responseMassage;


    @Transactional
    @Override
    public Map<String, Object> login(AuthenticationRequest authRequest) {
        String email, password, role;
        int userId;

        email = authRequest.getEmail();
        User user = userRepository.getByEmail(email)
                .orElseThrow(() -> new JwtAuthenticationException("Incorrect email", HttpStatus.UNAUTHORIZED));

        password = authRequest.getPassword();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.info("Password is not valid");
            throw new JwtAuthenticationException("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        userId = user.getId();
        role = user.getAuthority().getRole();
        String token = jwtTokenProvider.generateToken(userId, password, email,role);

        return this.createAuthResponse(userId, token);

    }

    @Override
    public void register(RegisterRequest registerRequest) throws DuplicateDataException {
        String email = registerRequest.getEmail();

        if (userRepository.getByEmail(email).isPresent()) {
            log.info("An account with email: {} already exists", email);
            throw new DuplicateDataException("An account with current email already exists");
        }

        User user = new User();

        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setAuthority(registerRequest.getAuthority());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        String token = jwtTokenProvider.generateToken(user.getId(), user.getPassword(), user.getEmail(),user.getAuthority().getRole());

        log.info("Saving new user to the database");
        userRepository.save(user);
        responseMassage.sendSimpleMessage(user.getEmail(), token,"Verification code successfully resend. ");

    }

    @Override
    public void loggedOut(LogoutRequest logoutRequest) {
        String token = logoutRequest.getToken();
        jwtTokenCache.markLogoutToken(token);
    }

    private Map<String, Object> createAuthResponse(int userId, String jwtToken) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("userId", userId);
        response.put("token", jwtToken);

        return response;
    }
}