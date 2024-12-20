package com.api.v2.dtos;

import java.time.LocalDate;

public record PersonModificationDto(
        String firstName,
        String middleName,
        String lastName,
        LocalDate birthDate,
        String email,
        String phoneNumber,
        String gender
) {
}
