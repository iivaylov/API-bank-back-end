package io.axe.bank.services;

import io.axe.bank.exceptions.BankAuthError;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.models.User;
import io.axe.bank.repositories.UserDAO;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.services.impls.UserServiceImpl;
import io.axe.bank.services.mappers.UserDTOMapper;
import io.axe.bank.utils.PasswordEncryptionDecryption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserDAO mockRepositoryDAO;

    @Mock
    private UserDTOMapper userDTOMapper;

    @Mock
    private PasswordEncryptionDecryption password;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void testGetUserByEmail_UserNotFound() {
        String email = "nonexistent@example.com";

        Mockito.when(mockRepositoryDAO.selectUserByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(BankEntityNotFound.class, () -> service.getUserByEmail(email));
    }

    @Test
    void testCheckCurrentUser() {
        // Prepare test data
        UserDTO currentUser = new UserDTO(
                "User1",
                "User2",
                "user@example.com",
                "111111");
        Integer userId = 1;
        User userFromRepository = new User();
        userFromRepository.setId(userId);
        userFromRepository.setEmail("user@example.com");

        // Mock the behavior of the userDAO
        Mockito.when(mockRepositoryDAO.selectUserById(userId)).thenReturn(Optional.of(userFromRepository));

        // Call the method to be tested
        Assertions.assertDoesNotThrow(() -> service.checkCurrentUser(currentUser, userId));

        // Verify that userDAO.selectUserById was called with the correct userId
        Mockito.verify(mockRepositoryDAO, Mockito.times(1))
                .selectUserById(userId);
    }

    @Test
    void testCloseUserProfile() {
        UserDTO currentUser = new UserDTO(
                "User1",
                "User2",
                "user@example.com",
                "111111");

        Integer userId = 1;

        User userFromRepository = new User();
        userFromRepository.setId(userId);
        userFromRepository.setEmail("user@example.com");

        Mockito.when(mockRepositoryDAO.selectUserById(userId))
                .thenReturn(Optional.of(userFromRepository));

        Assertions.assertDoesNotThrow(() -> service.closeUserProfile(currentUser, userId));

        Mockito.verify(mockRepositoryDAO, Mockito.times(1))
                .deleteUser(userFromRepository);
    }



}