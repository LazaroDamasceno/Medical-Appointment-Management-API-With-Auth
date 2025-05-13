package com.api.v1.common;

public class ErrorMessages {

    public static String customerNotFound() {
        return "Customer was not found.";
    }

    public static String duplicatedSin() {
        return "Provided Single Identification Number is currently in use.";
    }

    public static String duplicatedEmail() {
        return "Provided email is currently in use.";
    }
}
