package io.axe.bank.repositories.impls;

import io.axe.bank.models.Account;
import io.axe.bank.repositories.AccountDAO;
import io.axe.bank.repositories.jpa.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountJPADataAccessService implements AccountDAO {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountJPADataAccessService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> getAccountById(Integer accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    public void insertAccount(Account account) {
        accountRepository.save(account);
    }
}
