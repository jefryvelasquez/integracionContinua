package com.glovoapp.backender.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourierNotFoundException extends RuntimeException {

    public CourierNotFoundException(String s) {
        super(s);
    }
}
