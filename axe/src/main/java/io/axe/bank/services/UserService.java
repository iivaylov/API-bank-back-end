package io.axe.bank.services;

import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.services.dtos.UserDTO;

public interface UserService {

    UserDTO getUserByEmail(String email);

    boolean isEmailExists(String email);

    UserDTO verifyLoginRequest(String email, String password);

    void processRegistration(RegisterRequest registerRequest);

    void checkCurrentUser(UserDTO currentUser, Integer userId);

    void closeUserProfile(UserDTO currentUser, Integer userId);
}
