package io.axe.bank.services;

import io.axe.bank.Helpers;
import io.axe.bank.controllers.requests.TransferRequest;
import io.axe.bank.models.Account;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.impls.TransferServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransferServiceTests {

    @Mock
    private AccountDAO mockRepositoryDAO;

    @InjectMocks
    private TransferServiceImpl service;

    @Test
    void testInnerTransferFunds() {
        // Test data
        AccountDTO fromAccountDTO = Helpers.createMockFromAccountDTO();
        Account senderAccount = Helpers.createMockSenderAccount();
        TransferRequest transferRequest = Helpers.transfer();
        Account receiverAccount = Helpers.createMockReceiverAccount();

        Mockito.when(mockRepositoryDAO.getAccountById(fromAccountDTO.id())).thenReturn(Optional.of(senderAccount));
        Mockito.when(mockRepositoryDAO.getAccountByIban(transferRequest.getAccountToIban()))
                .thenReturn(Optional.of(receiverAccount));

        service.innerTransferFunds(transferRequest, fromAccountDTO);

        Mockito.verify(mockRepositoryDAO).getAccountById(fromAccountDTO.id());
        Mockito.verify(mockRepositoryDAO).getAccountByIban(transferRequest.getAccountToIban());
        Mockito.verify(mockRepositoryDAO).updateAccount(senderAccount);
        Mockito.verify(mockRepositoryDAO).updateAccount(receiverAccount);

        double expectedSenderBalance = 500.0;
        double expectedReceiverBalance = 1000.0;
        assert senderAccount.getBalance() == expectedSenderBalance;
        assert receiverAccount.getBalance() == expectedReceiverBalance;
    }
}
