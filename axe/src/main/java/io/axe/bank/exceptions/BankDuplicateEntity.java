package io.axe.bank.exceptions;

public class BankDuplicateEntity extends RuntimeException {

    public BankDuplicateEntity(String message) {
        super(message);
    }
}
