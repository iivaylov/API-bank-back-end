package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.WithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WithdrawController {

    @PostMapping("/accounts/{accountId}/withdraw")
    public ResponseEntity<String> withdrawFunds(@RequestBody WithdrawRequest withdrawRequest,
                                                @PathVariable Integer accountId) {
        throw new UnsupportedOperationException();
    }
}
