package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.models.User;
import io.axe.bank.repositories.UserDAO;
import io.axe.bank.services.UserService;
import io.axe.bank.services.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean isEmailExists(String email) {
        return false;
    }

    @Override
    public UserDTO verifyLoginRequest(String email, String password) {
        return null;
    }

    @Override
    public UserDTO processRegistration(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public UserDTO addUser(User user) {
        return null;
    }

    @Override
    public UserDTO updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(String email) {

    }
}
