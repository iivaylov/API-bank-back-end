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

    public static final double ZERO = 0.00;
    public static final String WITHDRAW_ERROR_MSG = "Incorrect amount to withdraw.";
    public static final String ACCOUNT_ERROR = "Account not found";
    private final AccountDAO accountDAO;

    @Autowired
    public WithdrawServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void withdrawMoneyFromAccount(WithdrawRequest withdrawRequest, AccountDTO userAccount) {
        Account account = getAccountByIdOrThrow(userAccount.id());

        double balance = account.getBalance();
        double withdrawFunds = withdrawRequest.getAmountToWithdraw();

        validateWithdrawAmount(withdrawFunds, balance);

        updateAccountBalance(account, balance, withdrawFunds);
    }

    private Account getAccountByIdOrThrow(int accountId) {
        return accountDAO.getAccountById(accountId)
                .orElseThrow(() -> new BankEntityNotFound(ACCOUNT_ERROR));
    }

    private void validateWithdrawAmount(double withdrawFunds, double balance) {
        if (withdrawFunds <= ZERO || withdrawFunds > balance) {
            throw new BankTransactionError(WITHDRAW_ERROR_MSG);
        }
    }

    private void updateAccountBalance(Account account, double balance, double withdrawFunds) {
        double newBalance = balance - withdrawFunds;
        double roundedValue = Math.round(newBalance * 100.0) / 100.0;
        account.setBalance(roundedValue);
        accountDAO.updateAccount(account);
    }
}
