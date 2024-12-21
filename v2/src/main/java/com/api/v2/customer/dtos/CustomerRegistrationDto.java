package com.api.v2.customer.dtos;

import com.api.v2.common.AddressDto;
import com.api.v2.people.dtos.PersonRegistrationDto;

public record CustomerRegistrationDto(
        PersonRegistrationDto personRegistrationDto,
        AddressDto addressDto
) {
}
