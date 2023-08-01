package io.axe.bank.services.impls;

import io.axe.bank.controllers.requests.OpenAccountRequest;
import io.axe.bank.services.AccountService;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public AccountDTO openAccount(OpenAccountRequest openAccountRequest, UserDTO user) {
        return null;
    }

    @Override
    public AccountDTO getAccount(Integer accountId, UserDTO user) {
        return null;
    }

    @Override
    public void deleteAccount(Integer accountId, UserDTO user) {

    }
}
