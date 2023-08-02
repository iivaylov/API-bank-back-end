package io.axe.bank.services;

import io.axe.bank.controllers.requests.WithdrawRequest;
import io.axe.bank.services.dtos.AccountDTO;

public interface WithdrawService {

    void withdrawMoneyFromAccount(WithdrawRequest withdrawRequest, AccountDTO account);
}
