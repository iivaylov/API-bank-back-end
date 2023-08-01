package io.axe.bank.repositories;

import io.axe.bank.models.Account;

public interface AccountDAO {

    void insertAccount(Account account);
}
