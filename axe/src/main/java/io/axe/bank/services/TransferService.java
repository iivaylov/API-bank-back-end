package io.axe.bank.services;

import io.axe.bank.controllers.requests.TransferRequest;
import io.axe.bank.services.dtos.AccountDTO;

public interface TransferService {

    void innerTransferFunds(TransferRequest transferRequest, AccountDTO fromAccount);
}
