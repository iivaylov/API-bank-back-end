package io.axe.bank.services;

import io.axe.bank.Helpers;
import io.axe.bank.controllers.requests.DepositRequest;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.exceptions.BankTransactionError;
import io.axe.bank.models.Account;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.impls.DepositServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DepositServiceTests {

    @Mock
    private AccountDAO mockRepositoryDAO;

    @InjectMocks
    private DepositServiceImpl depositService;

    @Test
    void testAddMoneyToAccount() {
        AccountDTO userAccountDTO = Helpers.createAccountDTO();
        Account account = Helpers.createMockAccount();
        DepositRequest depositRequest = Helpers.deposit();

        Mockito.when(mockRepositoryDAO.getAccountById(userAccountDTO.id())).thenReturn(Optional.of(account));

        depositService.addMoneyToAccount(depositRequest, userAccountDTO);

        Mockito.verify(mockRepositoryDAO).getAccountById(userAccountDTO.id());
        Mockito.verify(mockRepositoryDAO).updateAccount(account);

        double expectedBalance = 150.0;
        assert account.getBalance() == expectedBalance;
    }

    @Test
    void testAddMoneyToAccountMinimumDeposit() {
        AccountDTO userAccountDTO = Helpers.createAccountDTO();
        Account account = Helpers.createMockAccount();
        DepositRequest depositRequest = new DepositRequest(10.0);

        Mockito.when(mockRepositoryDAO.getAccountById(userAccountDTO.id())).thenReturn(Optional.of(account));

        try {
            depositService.addMoneyToAccount(depositRequest, userAccountDTO);
        } catch (BankTransactionError e) {
            assert e.getMessage().equals(DepositServiceImpl.DEPOSIT_ERROR);
        }

        Mockito.verify(mockRepositoryDAO).getAccountById(userAccountDTO.id());
        Mockito.verify(mockRepositoryDAO, Mockito.never()).updateAccount(Mockito.any());
    }

    @Test
    void testAddMoneyToAccountAccountNotFound() {
        AccountDTO userAccountDTO = Helpers.createAccountDTO();
        DepositRequest depositRequest = new DepositRequest(20.0);

        Mockito.when(mockRepositoryDAO.getAccountById(userAccountDTO.id())).thenReturn(Optional.empty());

        try {
            depositService.addMoneyToAccount(depositRequest, userAccountDTO);
        } catch (BankEntityNotFound e) {
            assert e.getMessage().equals(DepositServiceImpl.ACCOUNT_ERROR);
        }

        Mockito.verify(mockRepositoryDAO).getAccountById(userAccountDTO.id());
        Mockito.verify(mockRepositoryDAO, Mockito.never()).updateAccount(Mockito.any());
    }
}
