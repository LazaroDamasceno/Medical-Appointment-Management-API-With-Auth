package com.api.v1.common;

public class ErrorMessages {

    public static String customerNotFoundMessage(String id) {
        return "Customer whose id is %s was not found.".formatted(id);
    }

}
