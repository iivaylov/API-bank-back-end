package io.axe.bank.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountTransactionController {

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<String> getAccountTransactions(@PathVariable Integer accountId){
        throw new UnsupportedOperationException();
    }
}
