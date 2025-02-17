package com.vn.club_manager.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
@Setter
public class BadRequestException extends RuntimeException {

    private String fieldName;
    private Object fieldValue;
    public BadRequestException(String fieldName, Object fieldValue, String message) {
        super(String.format("%s cannot be '%s' : %s", fieldName, fieldValue, message));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}