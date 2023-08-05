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
    public static final String DEPOSIT_ERROR = "You must deposit more than 15.00$";
    public static final String ACCOUNT_ERROR = "Account not found";
    public static final double AMOUNT_TO_DEPOSIT = 15.00;
    private final AccountDAO accountDAO;

    @Autowired
    public DepositServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void addMoneyToAccount(DepositRequest depositRequest, AccountDTO userAccount) {
        Account account = getAccountById(userAccount.id());

        double funds = depositRequest.getAmountToDeposit();
        checkMinimumDepositAmount(funds);

        updateAccountBalance(account, funds);
    }

    private Account getAccountById(Integer accountId) {
        return accountDAO.getAccountById(accountId)
                .orElseThrow(() -> new BankEntityNotFound(ACCOUNT_ERROR));
    }

    private void checkMinimumDepositAmount(double amount) {
        if (amount <= AMOUNT_TO_DEPOSIT) {
            throw new BankTransactionError(DEPOSIT_ERROR);
        }
    }

    private void updateAccountBalance(Account account, double funds) {
        double balance = account.getBalance();
        double newBalance = balance + funds;
        double roundedValue = Math.round(newBalance * 100.0) / 100.0;
        account.setBalance(roundedValue);
        accountDAO.updateAccount(account);
    }
}
