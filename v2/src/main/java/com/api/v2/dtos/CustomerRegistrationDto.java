package com.api.v2.dtos;

import com.api.v2.common.AddressDto;

public record CustomerRegistrationDto(
        PersonRegistrationDto personRegistrationDto,
        AddressDto addressDto
) {
}
