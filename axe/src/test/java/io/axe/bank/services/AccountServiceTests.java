package io.axe.bank.services;

import io.axe.bank.Helpers;
import io.axe.bank.models.Account;
import io.axe.bank.models.User;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.repositories.UserDAO;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.services.impls.AccountServiceImpl;
import io.axe.bank.services.mappers.AccountDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

    @Mock
    private UserDAO mockUserRepositoryDAO;

    @Mock
    private AccountDAO mockAccountRepositoryDAO;

    @Mock
    private AccountDTOMapper accountDTOMapper;

    @InjectMocks
    private AccountServiceImpl service;

    @Test
    void testGetAccount(){
        UserDTO currentUser = Helpers.createUserDTO();
        User user = Helpers.createMockUserWithAccounts();
        Integer accountId = 1;
        Account account = Helpers.createMockAccount();
        AccountDTO accountDTO = Helpers.createAccountDTO();

        Mockito.when(mockUserRepositoryDAO.selectUserByEmail(currentUser.email())).thenReturn(Optional.of(user));
        Mockito.when(mockAccountRepositoryDAO.getAccountById(accountId)).thenReturn(Optional.of(account));
        Mockito.when(accountDTOMapper.apply(account)).thenReturn(accountDTO);

        AccountDTO result = service.getAccount(accountId, currentUser);

        Mockito.verify(mockUserRepositoryDAO).selectUserByEmail(currentUser.email());
        Mockito.verify(mockAccountRepositoryDAO).getAccountById(accountId);
        Mockito.verify(accountDTOMapper).apply(account);

        assert result != null;
        assert Objects.equals(result.id(), account.getId());
        assert result.iban().equals(account.getIban());
    }

    @Test
    void testDeleteAccount() {
        // Test data
        UserDTO currentUser = Helpers.createUserDTO();
        Integer accountId = 1;
        Account account = Helpers.createMockAccount();
        User user = new User();
        List<Account> userAccounts = new ArrayList<>();
        userAccounts.add(account);
        user.setAccounts(userAccounts);

        Mockito.when(mockUserRepositoryDAO.selectUserByEmail(currentUser.email())).thenReturn(Optional.of(user));
        Mockito.when(mockAccountRepositoryDAO.getAccountById(accountId)).thenReturn(Optional.of(account));
        Mockito.doAnswer(invocation -> {
            Account acc = invocation.getArgument(0);
            userAccounts.removeIf(a -> a.getId().equals(acc.getId()));
            return null;
        }).when(mockAccountRepositoryDAO).deleteAccount(Mockito.any());

        service.deleteAccount(accountId, currentUser);

        Mockito.verify(mockUserRepositoryDAO).selectUserByEmail(currentUser.email());
        Mockito.verify(mockAccountRepositoryDAO).getAccountById(accountId);
        Mockito.verify(mockAccountRepositoryDAO).deleteAccount(Mockito.any());
    }
}
