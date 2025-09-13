package com.api.v1;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException(String email) {
        super(ErrorMessage.duplicatedEmail(email));
    }
}
