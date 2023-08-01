package io.axe.bank.exceptions;

public class BankEntityNotFound extends RuntimeException {

    public BankEntityNotFound(String message) {
        super(message);
    }
}
