package io.axe.bank.repositories.jpa;

import io.axe.bank.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
