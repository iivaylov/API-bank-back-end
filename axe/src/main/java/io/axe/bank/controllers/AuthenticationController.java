package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.LoginRequest;
import io.axe.bank.controllers.requests.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        throw new UnsupportedOperationException();
    }
}
