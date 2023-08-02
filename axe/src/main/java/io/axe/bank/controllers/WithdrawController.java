package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.WithdrawRequest;
import io.axe.bank.services.AccountService;
import io.axe.bank.services.WithdrawService;
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
public class WithdrawController {

    public static final String WITHDRAWAL_MSG = "You successfully withdraw funds from your account.";
    private final AuthenticationHelper authenticationHelper;
    private final WithdrawService withdrawService;
    private final AccountService accountService;

    @Autowired
    public WithdrawController(AuthenticationHelper authenticationHelper,
                              WithdrawService withdrawService,
                              AccountService accountService) {
        this.authenticationHelper = authenticationHelper;
        this.withdrawService = withdrawService;
        this.accountService = accountService;
    }

    @PostMapping("/accounts/{accountId}/withdraw")
    public ResponseEntity<String> withdrawFunds(@RequestBody WithdrawRequest withdrawRequest,
                                                @PathVariable Integer accountId,
                                                HttpSession session) {
        UserDTO currentUser = authenticationHelper.tryGetCurrentUser(session);
        AccountDTO userAccount = accountService.getAccount(accountId, currentUser);
        withdrawService.withdrawMoneyFromAccount(withdrawRequest, userAccount);
        return new ResponseEntity<>(WITHDRAWAL_MSG, HttpStatus.ACCEPTED);
    }
}
