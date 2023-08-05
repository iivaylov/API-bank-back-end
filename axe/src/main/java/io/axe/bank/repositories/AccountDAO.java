package io.axe.bank.repositories;

import io.axe.bank.models.Account;

import java.util.Optional;

public interface AccountDAO {

    Optional<Account> getAccountById(Integer accountId);

    Optional<Account> getAccountByIban(String iban);

    void updateAccount(Account account);

    void insertAccount(Account account);

    void deleteAccount(Account account);
}
