package io.axe.bank.services;

import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.models.User;
import io.axe.bank.services.dtos.UserDTO;

public interface UserService {

    UserDTO getUserByEmail(String email);

    boolean isEmailExists(String email);

    UserDTO verifyLoginRequest(String email, String password);

    void processRegistration(RegisterRequest registerRequest);

    UserDTO addUser(User user);

    UserDTO updateUser(User user);

    void deleteUser(String email);
}
