package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.models.User;
import io.axe.bank.repositories.UserDAO;
import io.axe.bank.services.UserService;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.utils.PasswordEncryptionDecryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncryptionDecryption passwordEncryptionDecryption;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncryptionDecryption passwordEncryptionDecryption) {
        this.userDAO = userDAO;
        this.passwordEncryptionDecryption = passwordEncryptionDecryption;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean isEmailExists(String email) {
        return userDAO.emailExists(email);
    }

    @Override
    public UserDTO verifyLoginRequest(String email, String password) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void processRegistration(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncryptionDecryption.encryptPassword(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .townName(registerRequest.getTownName())
                .country(registerRequest.getCountry())
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .build();

        userDAO.insertUser(user);
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
