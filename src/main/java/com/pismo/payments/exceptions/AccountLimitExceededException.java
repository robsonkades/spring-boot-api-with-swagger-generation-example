package com.pismo.payments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountLimitExceededException extends RuntimeException {
    public AccountLimitExceededException(Long id) {
        super(String.format("Account %d exceeded the limit", id));
    }
}
