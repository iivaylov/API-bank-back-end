package io.axe.bank.services;

import io.axe.bank.Helpers;
import io.axe.bank.controllers.requests.RegisterRequest;
import io.axe.bank.exceptions.BankAuthError;
import io.axe.bank.exceptions.BankDuplicateEntity;
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
    private PasswordEncryptionDecryption passwordEncryptionDecryption;

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
        UserDTO currentUser = Helpers.createUserDTO();
        User mockUser = Helpers.createMockUser();

        Mockito.when(mockRepositoryDAO.selectUserById(mockUser.getId())).thenReturn(Optional.of(mockUser));


        Assertions.assertDoesNotThrow(() -> service.checkCurrentUser(currentUser, mockUser.getId()));

        Mockito.verify(mockRepositoryDAO, Mockito.times(1))
                .selectUserById(mockUser.getId());
    }

    @Test
    void testCloseUserProfile() {
        UserDTO currentUser = Helpers.createUserDTO();
        User mockUser = Helpers.createMockUser();


        Mockito.when(mockRepositoryDAO.selectUserById(mockUser.getId()))
                .thenReturn(Optional.of(mockUser));

        Assertions.assertDoesNotThrow(() -> service.closeUserProfile(currentUser, mockUser.getId()));

        Mockito.verify(mockRepositoryDAO, Mockito.times(1))
                .deleteUser(mockUser);
    }

    @Test
    void testVerifyLoginRequest_Successful() {
        String emailRequest = "user@example.com";
        String passwordRequest = "SVI29IQF8YL";
        User mockUser = Helpers.createMockUser();
        UserDTO expectedUserDTO = Helpers.createUserDTO();

        Mockito.when(mockRepositoryDAO.selectUserByEmail(emailRequest)).thenReturn(Optional.of(mockUser));

        Mockito.when(passwordEncryptionDecryption.decryptPassword(mockUser.getPassword()))
                .thenReturn(passwordRequest);

        Mockito.when(userDTOMapper.apply(mockUser)).thenReturn(expectedUserDTO);


        UserDTO result = service.verifyLoginRequest(emailRequest, passwordRequest);

        Assertions.assertEquals(expectedUserDTO, result);
    }

    @Test
    void testVerifyLoginRequest_Failed_WrongPassword() {
        String emailRequest = "user@example.com";
        String passwordRequest = "wrong-password";
        User mockUser = Helpers.createMockUser();


        Mockito.when(mockRepositoryDAO.selectUserByEmail(emailRequest)).thenReturn(Optional.of(mockUser));

        Mockito.when(passwordEncryptionDecryption.decryptPassword(mockUser.getPassword()))
                .thenReturn("SVI29IQF8YL");

        Assertions.assertThrows(BankAuthError.class, () -> service.verifyLoginRequest(emailRequest, passwordRequest));
    }

    @Test
    void testProcessRegistration_EmailExists() {
        RegisterRequest registerRequest = Helpers.registration();

        Mockito.when(mockRepositoryDAO.emailExists(registerRequest.getEmail())).thenReturn(true);

        Assertions.assertThrows(BankDuplicateEntity.class, () -> service.processRegistration(registerRequest));
    }
}