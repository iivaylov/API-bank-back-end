package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.OpenAccountRequest;
import io.axe.bank.services.AccountService;
import io.axe.bank.services.dtos.AccountDTO;
import io.axe.bank.services.dtos.UserDTO;
import io.axe.bank.utils.AuthenticationHelper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    public static final String ACCOUNT_CLOSED = "You have successfully closed your account.";
    private final AuthenticationHelper authenticationHelper;
    private final AccountService accountService;

    @Autowired
    public AccountController(AuthenticationHelper authenticationHelper,
                             AccountService accountService) {
        this.authenticationHelper = authenticationHelper;
        this.accountService = accountService;
    }

    @PostMapping("/open-account")
    public ResponseEntity<AccountDTO> openAccount(@RequestBody OpenAccountRequest openAccountRequest,
                                                  HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        AccountDTO openedAccount = accountService.openAccount(openAccountRequest, currentUser);
        return new ResponseEntity<>(openedAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Integer accountId, HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        AccountDTO account = accountService.getAccount(accountId, currentUser);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> closeAccount(@PathVariable Integer accountId, HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        accountService.deleteAccount(accountId, currentUser);
        return new ResponseEntity<>(ACCOUNT_CLOSED, HttpStatus.ACCEPTED);
    }
}
