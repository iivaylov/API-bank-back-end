package io.axe.bank.services;

import io.axe.bank.controllers.requests.OpenAccountRequest;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.dtos.UserDTO;

public interface AccountService {

    AccountDTO openAccount(OpenAccountRequest openAccountRequest, UserDTO user);

    AccountDTO getAccount(Integer accountId, UserDTO user);

    void deleteAccount(Integer accountId, UserDTO user);
}
