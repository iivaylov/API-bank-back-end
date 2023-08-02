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
import java.util.Objects;
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
    public AccountDTO getAccount(Integer accountId, UserDTO currentUser) {
        User user = getUserFromRepository(currentUser);

        if(user.getAccounts().stream().noneMatch(account -> account.getId().intValue() == accountId)){
            throw new BankEntityNotFound("You don't have such account.");
        }

       Account account = accountDAO.getAccountById(accountId).orElseThrow();

        return accountDTOMapper.apply(account);
    }

    @Override
    public AccountDTO openAccount(OpenAccountRequest openAccountRequest, UserDTO currentUser) {
        User user = getUserFromRepository(currentUser);
        AccountType accountType = openAccountRequest.getAccountType();

        if(userAccountTypeExists(user, accountType)){
            throw new BankDuplicateEntity(OPEN_ACCOUNT_ERROR_MSG);
        }

        Account account = Account
                .builder()
                .iban(generateIban())
                .accountType(accountType)
                .accountStatus(AccountStatus.ENABLED)
                .balance(INITIAL_BALANCE)
                .currency(USD)
                .isClosed(false)
                .build();

        accountDAO.insertAccount(account);
        addAccountToUserAccounts(account, user);
        return accountDTOMapper.apply(account);
    }

    @Override
    public void deleteAccount(Integer accountId, UserDTO currentUser) {
        User user = getUserFromRepository(currentUser);

        Account account = accountDAO.getAccountById(accountId)
                .orElseThrow(() -> new BankEntityNotFound("Account not found."));

        if(user.getAccounts().stream().noneMatch(acc -> Objects.equals(acc.getId(), accountId))){
            throw new BankEntityNotFound("You can close only your accounts!");
        }
        removeAccountFromUserAccounts(account, user);
        accountDAO.deleteAccount(account);
    }

    private User getUserFromRepository(UserDTO currentUser) {
        String email = currentUser.email();
        return userDAO.selectUserByEmail(email)
                .orElseThrow(() -> new BankEntityNotFound("User not found."));
    }

    private void addAccountToUserAccounts(Account account, User user) {
        Account newAccount = accountDAO.getAccountById(account.getId())
                .orElseThrow(() -> new BankEntityNotFound("Account not found."));

        user.getAccounts().add(newAccount);
        userDAO.updateUser(user);
    }

    private void removeAccountFromUserAccounts(Account account, User user) {
        user.getAccounts().remove(account);
        userDAO.updateUser(user);
    }

    private boolean userAccountTypeExists(User user, AccountType accountType) {
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
