package com.api.v1.common;

import lombok.Getter;

@Getter
public enum ErrorMessages {

    CUSTOMER_NOT_FOUND("Customer was not found."),
    DUPLICATED_SIN("Provided SIN is already in use."),
    DUPLICATED_EMAIL("Provided email is already in use.");

    private final String value;

    ErrorMessages(String value) {
        this.value = value;
    }
}
