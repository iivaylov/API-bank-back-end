package io.axe.bank.exceptions;

public class BankTransactionError extends RuntimeException {
    public BankTransactionError(String message) {
        super(message);
    }
}
