package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.OpenAccountRequest;
import io.axe.bank.exceptions.BankDuplicateEntity;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.models.Account;
import io.axe.bank.models.User;
import io.axe.bank.models.enums.AccountStatus;
import io.axe.bank.models.enums.AccountType;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.repositories.UserDAO;
import io.axe.bank.services.AccountService;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.services.mappers.AccountDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private static final String GENERATOR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String OPEN_ACCOUNT_ERROR_MSG = "You cannot open a second account of this type.";
    public static final String USD = "USD";
    public static final double INITIAL_BALANCE = 0.00;

    private final UserDAO userDAO;
    private final AccountDAO accountDAO;
    private final AccountDTOMapper accountDTOMapper;

    @Autowired
    public AccountServiceImpl(UserDAO userDAO, AccountDAO accountDAO, AccountDTOMapper accountDTOMapper) {
        this.userDAO = userDAO;
        this.accountDAO = accountDAO;
        this.accountDTOMapper = accountDTOMapper;
    }

    @Override
    public AccountDTO openAccount(OpenAccountRequest openAccountRequest, UserDTO currentUser) {
        String email = currentUser.email();
        User user = userDAO.selectUserByEmail(email).orElseThrow();
        AccountType accountType = openAccountRequest.getAccountType();

        if(userAccountExists(user, accountType)){
            throw new BankDuplicateEntity(OPEN_ACCOUNT_ERROR_MSG);
        }

        Account account = Account
                .builder()
                .iban(generateIban())
                .accountType(accountType)
                .accountStatus(AccountStatus.ENABLED)
                .balance(INITIAL_BALANCE)
                .currency(USD)
                .build();

        accountDAO.insertAccount(account);
        addAccountToUserAccounts(account, user);
        return accountDTOMapper.apply(account);
    }

    private void addAccountToUserAccounts(Account account, User user) {
       Account newAccount = accountDAO.getAccountById(account.getId())
               .orElseThrow(() -> new BankEntityNotFound("Account not found"));

        user.getAccounts().add(newAccount);
        userDAO.updateUser(user);
    }

    @Override
    public AccountDTO getAccount(Integer accountId, UserDTO user) {
        return null;
    }

    @Override
    public void deleteAccount(Integer accountId, UserDTO user) {

    }

    private boolean userAccountExists(User user, AccountType accountType) {
        return user.getAccounts()
                .stream()
                .anyMatch(account -> account.getAccountType().equals(accountType));
    }

    private String generateIban(){
        final StringBuilder sb = new StringBuilder("AXE#");
        final Random random = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(GENERATOR.length());
            char randomChar = GENERATOR.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString().toUpperCase().trim();
    }


}
