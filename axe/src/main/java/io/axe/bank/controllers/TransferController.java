package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.TransferRequest;
import io.axe.bank.services.AccountService;
import io.axe.bank.services.TransferService;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.utils.AuthenticationHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TransferController {

    public static final String TRANSFER_MSG = "You successfully transfer funds from your account.";
    private final AuthenticationHelper authenticationHelper;
    private final TransferService transferService;
    private final AccountService accountService;

    @Autowired
    public TransferController(AuthenticationHelper authenticationHelper,
                              TransferService transferService, AccountService accountService) {
        this.authenticationHelper = authenticationHelper;
        this.transferService = transferService;
        this.accountService = accountService;
    }

    @PostMapping("/accounts/{fromAccountId}/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest transferRequest,
                                                @PathVariable Integer fromAccountId, HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        AccountDTO fromAccount = accountService.getAccount(fromAccountId, currentUser);
        transferService.innerTransferFunds(transferRequest, fromAccount);
        return new ResponseEntity<>(TRANSFER_MSG, HttpStatus.ACCEPTED);
    }
}
