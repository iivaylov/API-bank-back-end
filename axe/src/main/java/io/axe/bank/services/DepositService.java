package io.axe.bank.services;

import io.axe.bank.controllers.requests.DepositRequest;
import io.axe.bank.services.dtos.AccountDTO;

public interface DepositService {

    void addMoneyToAccount(DepositRequest depositRequest, AccountDTO account);
}
