package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.exceptions.BankAuthError;
import io.axe.bank.exceptions.BankDuplicateEntity;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.models.User;
import io.axe.bank.repositories.UserDAO;
import io.axe.bank.services.UserService;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.services.mappers.UserDTOMapper;
import io.axe.bank.utils.PasswordEncryptionDecryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    public static final String EMAIL_EXISTS_MSG = "Email already exists.";
    public static final String USER_ERROR = "User not found.";
    public static final String WRONG_PASSWORD = "Wrong Password.";
    public static final String CLOSE_PROFILE_ERROR = "You can close only your profile.";
    public static final String VIEW_PROFILE_ERROR = "You can view only your profile.";
    private final UserDAO userDAO;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncryptionDecryption passwordEncryptionDecryption;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserDTOMapper userDTOMapper,
                           PasswordEncryptionDecryption passwordEncryptionDecryption) {
        this.userDAO = userDAO;
        this.userDTOMapper = userDTOMapper;
        this.passwordEncryptionDecryption = passwordEncryptionDecryption;
    }

    private static void validateEmail(UserDTO currentUser, User userFromRepository, String message) {
        String userEmail = userFromRepository.getEmail();
        if (!userEmail.equals(currentUser.email())) {
            throw new BankAuthError(message);
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userDAO
                .selectUserByEmail(email)
                .map(userDTOMapper)
                .orElseThrow(() -> new BankEntityNotFound(USER_ERROR));
    }

    @Override
    public boolean isEmailExists(String email) {
        return userDAO.emailExists(email);
    }

    @Override
    public UserDTO verifyLoginRequest(String email, String password) {
        User user = userDAO.selectUserByEmail(email).orElseThrow();
        String originalPassword = passwordEncryptionDecryption.decryptPassword(user.getPassword());

        if (!originalPassword.equals(password)) {
            throw new BankAuthError(WRONG_PASSWORD);
        }
        return userDTOMapper.apply(user);
    }

    @Override
    public void processRegistration(RegisterRequest registerRequest) {
        validateEmailNotExists(registerRequest.getEmail());
        User user = createUserFromRequest(registerRequest);
        userDAO.insertUser(user);
    }

    @Override
    public void checkCurrentUser(UserDTO currentUser, Integer userId) {
        User userFromRepository = getUserById(userId);
        validateEmail(currentUser, userFromRepository, VIEW_PROFILE_ERROR);
    }

    @Override
    public void closeUserProfile(UserDTO currentUser, Integer userId) {
        User userFromRepository = getUserById(userId);
        validateEmail(currentUser, userFromRepository, CLOSE_PROFILE_ERROR);
        userFromRepository.setClosedAt(LocalDateTime.now());
        userDAO.deleteUser(userFromRepository);
    }

    private void validateEmailNotExists(String email) {
        if (userDAO.emailExists(email)) {
            throw new BankDuplicateEntity(EMAIL_EXISTS_MSG);
        }
    }

    private User getUserById(Integer userId) {
        return userDAO.selectUserById(userId)
                .orElseThrow(() -> new BankEntityNotFound(USER_ERROR));
    }

    private User createUserFromRequest(RegisterRequest registerRequest) {
        String encryptedPassword = passwordEncryptionDecryption.encryptPassword(registerRequest.getPassword());

        return User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(encryptedPassword)
                .phoneNumber(registerRequest.getPhoneNumber())
                .townName(registerRequest.getTownName())
                .country(registerRequest.getCountry())
                .isDeleted(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
