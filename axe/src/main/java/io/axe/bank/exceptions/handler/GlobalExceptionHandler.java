package io.axe.bank.exceptions.handler;

import io.axe.bank.exceptions.BankAuthError;
import io.axe.bank.exceptions.BankDuplicateEntity;
import io.axe.bank.exceptions.BankEntityNotFound;
import io.axe.bank.exceptions.BankPassError;
import io.axe.bank.exceptions.model.BankErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BankAuthError.class})
    public ResponseEntity<Object> handleAuthorizationException
            (BankAuthError bankAuthError) {
        BankErrorMessage errorMessage = new BankErrorMessage(
                bankAuthError.getMessage(),
                HttpStatus.FORBIDDEN
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {BankPassError.class})
    public ResponseEntity<Object> handleBankPasswordException
            (BankPassError bankPassError) {
        BankErrorMessage errorMessage = new BankErrorMessage(
                bankPassError.getMessage(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BankEntityNotFound.class})
    public ResponseEntity<Object> handleBankEntityNotFoundException
            (BankEntityNotFound bankEntityNotFound) {
        BankErrorMessage errorMessage = new BankErrorMessage(
                bankEntityNotFound.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BankDuplicateEntity.class})
    public ResponseEntity<Object> handleBankDuplicateEntityException
            (BankDuplicateEntity bankDuplicateEntity) {
        BankErrorMessage errorMessage = new BankErrorMessage(
                bankDuplicateEntity.getMessage(),
                HttpStatus.CONFLICT
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}

