package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.DepositRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DepositController {

    @PostMapping("/accounts/{accountId}/deposit")
    public ResponseEntity<String> depositFunds(@RequestBody DepositRequest depositRequest,
                                               @PathVariable Integer accountId) {
        throw new UnsupportedOperationException();
    }
}
