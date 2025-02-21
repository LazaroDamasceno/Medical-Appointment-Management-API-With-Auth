package com.api.v2.cards.dtos;

import java.time.LocalDate;

import com.api.v2.common.Id;

public record CardResponseDto(
        @Id String id,
        String type,
        String bank,
        String cvv_cvc,
        LocalDate dueDate
) {
}
