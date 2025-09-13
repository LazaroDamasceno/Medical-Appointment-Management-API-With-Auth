package com.api.v1;

public class ErrorMessage {

    public static String duplicatedMedicalLicense(String licenseNumber, String state) {
        return "Medical license number whose both number are %s and %s are currently in use".formatted(licenseNumber, state);
    }

    public static String unknowUsState(String stateCode) {
        return "The US State's code %s does not exist.".formatted(stateCode);
    }

    public static String duplicatedSin(String sin) {
        return "The provided SIN %s is currently in use.".formatted(sin);
    }

    public static String duplicatedEmail(String email) {
        return "The provided email %s is currently in use.".formatted(email);
    }
}
