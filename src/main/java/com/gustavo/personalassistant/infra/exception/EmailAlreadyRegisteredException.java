package com.gustavo.personalassistant.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyRegisteredException extends RuntimeException {

    private EmailAlreadyRegisteredException(String message) {
        super(message);
    }

    public static EmailAlreadyRegisteredException emailAlreadyRegistered() {
        return new EmailAlreadyRegisteredException("This email address is already registered");
    }

}
