package io.axe.bank.exceptions;

public class BankPassError extends RuntimeException {
    public BankPassError(String message) {
        super(message);
    }
}
