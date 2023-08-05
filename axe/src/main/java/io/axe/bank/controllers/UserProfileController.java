package io.axe.bank.controllers;

import io.axe.bank.services.UserService;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.utils.AuthenticationHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserProfileController {

    public static final String CLOSED_PROFILE_MSG = "You successfully closed your profile.";
    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;

    @Autowired
    public UserProfileController(AuthenticationHelper authenticationHelper, UserService userService) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable Integer userId, HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        userService.checkCurrentUser(currentUser, userId);
        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Integer userId, HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        userService.closeUserProfile(currentUser, userId);
        session.invalidate();
        return new ResponseEntity<>(CLOSED_PROFILE_MSG, HttpStatus.ACCEPTED);
    }
}
