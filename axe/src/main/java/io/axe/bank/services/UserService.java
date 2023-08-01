package io.axe.bank.services;

import io.axe.bank.models.User;
import io.axe.bank.services.dtos.UserDTO;

public interface UserService {

    UserDTO getUserByEmail(String email);

    UserDTO addUser(User user);

    UserDTO updateUser(User user);

    void deleteUser(String email);
}
