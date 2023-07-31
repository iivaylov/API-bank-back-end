package io.axe.bank.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {

    @GetMapping("/{userId}")
    public ResponseEntity<String> getUserProfile(@PathVariable Integer userId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUserProfile(@PathVariable Integer userId) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Integer userId) {
        throw new UnsupportedOperationException();
    }
}
