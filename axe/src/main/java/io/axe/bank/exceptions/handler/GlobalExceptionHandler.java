package io.axe.bank.exceptions.handler;

import io.axe.bank.exceptions.BankAuthError;
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
    public ResponseEntity<Object> handleAuthorizationException
            (BankPassError bankPassError) {
        BankErrorMessage errorMessage = new BankErrorMessage(
                bankPassError.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

