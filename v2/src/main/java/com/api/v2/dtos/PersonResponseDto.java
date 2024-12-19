package com.api.v2.dtos;

import java.time.LocalDate;

public record PersonResponseDto(
        String fullName,
        LocalDate birthDate,
        String ssn,
        String email,
        String phoneNumber,
        String gender
) {
}
