package com.bankaccount.bankaccount.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientFundsException extends Exception{

    public InsufficientFundsException(String message) {
        super(message);
    }

}