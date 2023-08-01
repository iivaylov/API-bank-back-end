package io.axe.bank.exceptions.model;

import org.springframework.http.HttpStatus;

public record BankErrorMessage(
        String message,

        HttpStatus httpStatus
) {
}

