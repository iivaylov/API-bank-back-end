package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.DepositRequest;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.exceptions.BankTransactionError;
import io.axe.bank.models.Account;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.services.DepositService;
import io.axe.bank.services.dtos.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositServiceImpl implements DepositService {
    private final AccountDAO accountDAO;

    @Autowired
    public DepositServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void addMoneyToAccount(DepositRequest depositRequest, AccountDTO userAccount) {
        Account account = accountDAO.getAccountById(userAccount.id())
                .orElseThrow(() -> new BankEntityNotFound("Account not found"));

        double balance = account.getBalance();
        double funds = depositRequest.getAmountToDeposit();

        if(funds <= 15.00){
            throw new BankTransactionError("You must deposit more than 15.00$");
        }

        double newBalance = balance + funds;
        double roundedValue = Math.round(newBalance*100.0)/100.0;
        account.setBalance(roundedValue);
        accountDAO.updateAccount(account);
    }
}