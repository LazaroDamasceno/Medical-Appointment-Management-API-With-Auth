package com.api.v2.cards.dtos.exposed;

import java.time.LocalDate;

public record CardResponseDto(
        String id,
        String type,
        String bank,
        String cvv_cvc,
        LocalDate dueDate
) {
}
