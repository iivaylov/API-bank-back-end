package io.axe.bank.utils;

import io.axe.bank.controllers.requests.LoginRequest;
import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.exceptions.BankAuthError;
import io.axe.bank.exceptions.BankPassError;
import io.axe.bank.services.UserService;
import io.axe.bank.services.dtos.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static io.axe.bank.controllers.AuthenticationController.LOGGED_USER;

@Component
public class AuthenticationHelper {
    public static final String INVALID_AUTHENTICATION_EMAIL = "Email not found.";
    public static final String CONFIRM_PASS_ERROR_MSG = "The password confirmation should match the password.";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public UserDTO tryGetCurrentUser(HttpSession session) {
        String loggedUser = (String) session.getAttribute(LOGGED_USER);
        if (loggedUser == null) {
            throw new BankAuthError(INVALID_AUTHENTICATION_ERROR);
        }
        return userService.getUserByEmail(loggedUser);
    }

    public UserDTO verifyAuthentication(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        if (!userService.isEmailExists(email)) {
            throw new BankAuthError(INVALID_AUTHENTICATION_EMAIL);
        }
        String password = loginRequest.getPassword();
        return userService.verifyLoginRequest(email, password);
    }

    public void registerNewUser(RegisterRequest registerRequest) {
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new BankPassError(CONFIRM_PASS_ERROR_MSG);
        }
        userService.processRegistration(registerRequest);
    }
}
