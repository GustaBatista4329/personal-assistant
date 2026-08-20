package com.gustavo.personalassistant.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private NotFoundException(String message){
        super(message);
    }

    public static NotFoundException expenseNotFound() {
        return new NotFoundException("Expense not found!");
    }

    public static NotFoundException incomeNotFound(){
        return new NotFoundException("Income not found!");
    }
    public static NotFoundException userNotFound(){
        return new NotFoundException("User not found!");
    }

}
