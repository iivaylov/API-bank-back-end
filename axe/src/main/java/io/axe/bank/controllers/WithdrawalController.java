package io.axe.bank.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WithdrawalController {

    @PostMapping("/accounts/{accountId}/withdraw")
    public ResponseEntity<String> withdrawFunds(@PathVariable Integer accountId) {
        throw new UnsupportedOperationException();
    }
}
