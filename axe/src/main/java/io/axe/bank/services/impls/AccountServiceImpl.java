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
    public static final String OPEN_ACCOUNT_ERROR_MSG = "You cannot open a second account of this type.";
    public static final String USD = "USD";
    public static final double INITIAL_BALANCE = 0.00;
    public static final String USER_ACCOUNT_ERROR = "You don't have such account.";
    public static final String ACCOUNT_ERROR = "Account not found.";
    public static final int SYMBOLS = 16;
    public static final String CLOSING_ACCOUNT_ERROR = "You can close only your accounts!";
    public static final String USER_ERROR = "User not found.";
    public static final String IBAN_AXE_PART = "AXE#";
    public static final int ZERO = 0;
    private static final String GENERATOR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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
        validateUserAccountExists(user, accountId);
        Account account = getAccountById(accountId);
        return accountDTOMapper.apply(account);
    }

    @Override
    public AccountDTO openAccount(OpenAccountRequest openAccountRequest, UserDTO currentUser) {
        User user = getUserFromRepository(currentUser);
        validateUserAccountTypeNotExists(user, openAccountRequest.getAccountType());
        Account account = createAccount(openAccountRequest.getAccountType());
        addAccountToUserAccounts(account, user);
        return accountDTOMapper.apply(account);
    }

    @Override
    public void deleteAccount(Integer accountId, UserDTO currentUser) {
        User user = getUserFromRepository(currentUser);
        Account account = getAccountById(accountId);
        validateUserOwnsAccount(user, account);
        removeAccountFromUserAccounts(account, user);
        deleteAccountFromDAO(account);
    }

    private void validateUserAccountExists(User user, Integer accountId) {
        if (user.getAccounts().stream().noneMatch(account -> Objects.equals(account.getId(), accountId))) {
            throw new BankEntityNotFound(USER_ACCOUNT_ERROR);
        }
    }

    private void validateUserAccountTypeNotExists(User user, AccountType accountType) {
        if (user.getAccounts().stream().anyMatch(account -> account.getAccountType().equals(accountType))) {
            throw new BankDuplicateEntity(OPEN_ACCOUNT_ERROR_MSG);
        }
    }

    private Account getAccountById(Integer accountId) {
        return accountDAO.getAccountById(accountId)
                .orElseThrow(() -> new BankEntityNotFound(ACCOUNT_ERROR));
    }

    private void validateUserOwnsAccount(User user, Account account) {
        if (user.getAccounts().stream().noneMatch(acc -> Objects.equals(acc.getId(), account.getId()))) {
            throw new BankEntityNotFound(CLOSING_ACCOUNT_ERROR);
        }
    }

    private Account createAccount(AccountType accountType) {
        Account account = Account.builder()
                .iban(generateIban())
                .accountType(accountType)
                .accountStatus(AccountStatus.ENABLED)
                .balance(INITIAL_BALANCE)
                .currency(USD)
                .isClosed(false)
                .build();

        accountDAO.insertAccount(account);
        return account;
    }

    private void deleteAccountFromDAO(Account account) {
        accountDAO.deleteAccount(account);
    }

    private User getUserFromRepository(UserDTO currentUser) {
        String email = currentUser.email();
        return userDAO.selectUserByEmail(email)
                .orElseThrow(() -> new BankEntityNotFound(USER_ERROR));
    }

    private void addAccountToUserAccounts(Account account, User user) {
        Account newAccount = accountDAO.getAccountById(account.getId())
                .orElseThrow(() -> new BankEntityNotFound(ACCOUNT_ERROR));

        user.getAccounts().add(newAccount);
        userDAO.updateUser(user);
    }

    private void removeAccountFromUserAccounts(Account account, User user) {
        user.getAccounts().remove(account);
        userDAO.updateUser(user);
    }

    private String generateIban() {
        final StringBuilder sb = new StringBuilder(IBAN_AXE_PART);
        final Random random = new SecureRandom();
        for (int i = ZERO; i < SYMBOLS; i++) {
            int index = random.nextInt(GENERATOR.length());
            char randomChar = GENERATOR.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString().toUpperCase().trim();
    }
}
