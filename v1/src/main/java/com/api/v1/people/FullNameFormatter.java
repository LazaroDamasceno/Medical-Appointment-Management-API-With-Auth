package com.api.v1.people;

public final class FullNameFormatter {

    private FullNameFormatter() {
    }

    public static String format(Person person) {
        String firstName = person.firstName();
        String lastName = person.lastName();
        String middleName = person.middleName();
        if  (middleName == null) {
            return "%s %s".formatted(firstName, lastName);
        }
        return "%s %s %s".formatted(firstName, middleName, lastName);
    }

}
