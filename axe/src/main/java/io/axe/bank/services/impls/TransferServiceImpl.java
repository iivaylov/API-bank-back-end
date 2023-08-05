package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.TransferRequest;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.exceptions.BankTransactionError;
import io.axe.bank.models.Account;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.services.TransferService;
import io.axe.bank.services.dtos.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

    public static final String TRANSFER_ERROR_MSG = "The amount you want to send is greater than your balance!";
    private final AccountDAO accountDAO;

    @Autowired
    public TransferServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void innerTransferFunds(TransferRequest transferRequest, AccountDTO fromAccount) {
        Account senderAccount = getSenderAccount(fromAccount);
        Account receiverAccount = getReceiverAccount(transferRequest);

        double amountToSend = transferRequest.getAmountToTransfer();
        checkSufficientBalance(senderAccount, amountToSend);

        updateSenderBalance(senderAccount, amountToSend);
        updateReceiverBalance(receiverAccount, amountToSend);
    }

    private Account getSenderAccount(AccountDTO fromAccount) {
        return accountDAO.getAccountById(fromAccount.id())
                .orElseThrow(() -> new BankEntityNotFound("Account not found."));
    }

    private Account getReceiverAccount(TransferRequest transferRequest) {
        String accountToIban = transferRequest.getAccountToIban();
        return accountDAO.getAccountByIban(accountToIban)
                .orElseThrow(() -> new BankEntityNotFound("Account not found."));
    }

    private void checkSufficientBalance(Account senderAccount, double amountToSend) {
        double senderBalance = senderAccount.getBalance();
        if (senderBalance < amountToSend) {
            throw new BankTransactionError(TRANSFER_ERROR_MSG);
        }
    }

    private void updateSenderBalance(Account senderAccount, double amountToSend) {
        double senderBalance = senderAccount.getBalance();
        double newSenderBalance = senderBalance - amountToSend;
        double roundedBalance = Math.round(newSenderBalance * 100.0) / 100.0;
        senderAccount.setBalance(roundedBalance);
        accountDAO.updateAccount(senderAccount);
    }

    private void updateReceiverBalance(Account receiverAccount, double amountToSend) {
        double receiverBalance = receiverAccount.getBalance();
        double newReceiverBalance = receiverBalance + amountToSend;
        double roundedReceiverBalance = Math.round(newReceiverBalance * 100.0) / 100.0;
        receiverAccount.setBalance(roundedReceiverBalance);
        accountDAO.updateAccount(receiverAccount);
    }
}
