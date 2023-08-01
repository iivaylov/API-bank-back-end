package io.axe.bank.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountTransactionController {

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<String> getAccountTransactions(@RequestHeader HttpHeaders headers,
                                                         @PathVariable Integer accountId) {
        throw new UnsupportedOperationException();
    }
}
