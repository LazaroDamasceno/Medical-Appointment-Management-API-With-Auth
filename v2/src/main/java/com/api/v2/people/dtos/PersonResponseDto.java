package com.api.v2.people.dtos;

import com.api.v2.common.Id;

public record PersonResponseDto(
        String fullName,
        @Id String id
) {
}
