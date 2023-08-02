package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.WithdrawRequest;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.exceptions.BankTransactionError;
import io.axe.bank.models.Account;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.services.WithdrawService;
import io.axe.bank.services.dtos.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawServiceImpl implements WithdrawService {

    private final AccountDAO accountDAO;

    @Autowired
    public WithdrawServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void withdrawMoneyFromAccount(WithdrawRequest withdrawRequest, AccountDTO userAccount) {
        Account account = accountDAO.getAccountById(userAccount.id())
                .orElseThrow(() -> new BankEntityNotFound("Account not found"));

        double balance = account.getBalance();
        double withdrawFunds = withdrawRequest.getAmountToWithdraw();

        if(withdrawFunds <= 0.00 || withdrawFunds > balance){
            throw new BankTransactionError("Incorrect amount to withdraw.");
        }

        double newBalance = balance - withdrawFunds;
        double roundedValue = Math.round(newBalance*100.0)/100.0;
        account.setBalance(roundedValue);
        accountDAO.updateAccount(account);
    }
}
