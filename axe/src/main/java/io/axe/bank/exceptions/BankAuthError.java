package io.axe.bank.exceptions;

public class BankAuthError extends RuntimeException {

    public BankAuthError(String message) {
        super(message);
    }
}
