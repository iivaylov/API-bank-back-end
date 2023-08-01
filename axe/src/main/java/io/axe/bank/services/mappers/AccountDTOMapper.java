package io.axe.bank.services.mappers;

import io.axe.bank.models.Account;
import io.axe.bank.services.dtos.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccountDTOMapper implements Function<Account, AccountDTO> {
    @Override
    public AccountDTO apply(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getIban(),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getBalance(),
                account.getCurrency()
        );
    }
}
