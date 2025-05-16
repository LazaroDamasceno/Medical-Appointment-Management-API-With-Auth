package com.api.v1.common;

public enum ErrorMessages {

    CUSTOMER_NOT_FOUND("Customer was not found."),
    DUPLICATED_SIN("Provided SIN is already in use."),
    DUPLICATED_EMAIL("Provided email is already in use."),
    DOCTOR_NOT_FOUND("Doctor was not found."),
    DUPLICATED_LICENSE_NUMBER("Provided license number is already in use."),
    TERMINATED_DOCTOR("Doctor is currently terminated."),
    ACTIVE_DOCTOR("Doctor is currently active.");

    private final String value;

    ErrorMessages(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
