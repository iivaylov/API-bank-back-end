package io.axe.bank.services;

import io.axe.bank.Helpers;
import io.axe.bank.controllers.requests.WithdrawRequest;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.exceptions.BankTransactionError;
import io.axe.bank.models.Account;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.impls.WithdrawServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WithdrawServiceTests {

    @Mock
    private AccountDAO mockRepositoryDAO;

    @InjectMocks
    private WithdrawServiceImpl withdrawService;

    @Test
    void testWithdrawMoneyFromAccount_SuccessfulWithdrawal() {
        AccountDTO userAccountDTO = Helpers.createAccountDTO();
        double initialBalance = userAccountDTO.balance();
        WithdrawRequest withdrawRequest = Helpers.withdraw();
        Account account = Helpers.createMockAccount();

        Mockito.when(mockRepositoryDAO.getAccountById(userAccountDTO.id())).thenReturn(Optional.of(account));

        withdrawService.withdrawMoneyFromAccount(withdrawRequest, userAccountDTO);

        double expectedNewBalance = initialBalance - withdrawRequest.getAmountToWithdraw();
        Mockito.verify(mockRepositoryDAO)
                .updateAccount(Mockito.argThat(updatedAccount -> updatedAccount.getBalance() == expectedNewBalance));
    }

    @Test
    void testWithdrawMoneyFromAccount_InvalidWithdrawalAmount() {
        AccountDTO userAccountDTO = Helpers.createAccountDTO();
        double withdrawAmount = 200.00;
        Account account = Helpers.createMockAccount();

        Mockito.when(mockRepositoryDAO.getAccountById(userAccountDTO.id())).thenReturn(java.util.Optional.of(account));

        WithdrawRequest withdrawRequest = new WithdrawRequest(withdrawAmount);

        Assertions.assertThrows(BankTransactionError.class,
                () -> withdrawService.withdrawMoneyFromAccount(withdrawRequest, userAccountDTO),
                WithdrawServiceImpl.WITHDRAW_ERROR_MSG
        );

        Mockito.verify(mockRepositoryDAO, Mockito.never()).updateAccount(Mockito.any());
    }
}
