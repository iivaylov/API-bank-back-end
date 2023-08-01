package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.LoginRequest;
import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.utils.AuthenticationHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    public static final String LOGGED_USER = "loggedUser";
    public static final String LOGGED_IN_MESSAGE = "Logged in successfully.";
    public static final String LOGOUT_MESSAGE = "Logout successfully.";
    public static final String REGISTERED_MESSAGE = "Registered successfully.";

    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public AuthenticationController(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        authenticationHelper.tryAlreadyLoggedInUser(session);
        final UserDTO currentUser = authenticationHelper.verifyAuthentication(loginRequest);
        session.setAttribute(LOGGED_USER, currentUser.email());
        return new ResponseEntity<>(LOGGED_IN_MESSAGE, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        authenticationHelper.registerNewUser(registerRequest);
        return new ResponseEntity<>(REGISTERED_MESSAGE, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(LOGOUT_MESSAGE, HttpStatus.OK);
    }
}
