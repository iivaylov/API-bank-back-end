package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.DepositRequest;
import io.axe.bank.services.AccountService;
import io.axe.bank.services.DepositService;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.utils.AuthenticationHelper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DepositController {

    public static final String DEPOSIT_MSG = "You successfully deposit money to your account.";
    private final AuthenticationHelper authenticationHelper;
    private final DepositService depositService;
    private final AccountService accountService;

    @Autowired
    public DepositController(AuthenticationHelper authenticationHelper,
                             DepositService depositService, AccountService accountService) {
        this.authenticationHelper = authenticationHelper;
        this.depositService = depositService;
        this.accountService = accountService;
    }

    @PostMapping("/accounts/{accountId}/deposit")
    public ResponseEntity<String> depositFunds(@RequestBody DepositRequest depositRequest,
                                               @PathVariable Integer accountId,
                                               HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        AccountDTO userAccount = accountService.getAccount(accountId, currentUser);
        depositService.addMoneyToAccount(depositRequest, userAccount);
        return new ResponseEntity<>(DEPOSIT_MSG, HttpStatus.ACCEPTED);
    }
}
