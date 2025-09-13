package com.api.v1;

public class DuplicatedSinException extends RuntimeException {
    public DuplicatedSinException(String sin) {
        super(ErrorMessage.duplicatedSin(sin));
    }
}
