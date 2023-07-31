package io.axe.bank.controllers;

import io.axe.bank.controllers.requests.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransferController {

    @PostMapping("/accounts/{fromAccountId}/transfer")
    public ResponseEntity<String> depositFunds(@RequestBody TransferRequest transferRequest,
                                               @PathVariable Integer fromAccountId) {
        throw new UnsupportedOperationException();
    }
}
