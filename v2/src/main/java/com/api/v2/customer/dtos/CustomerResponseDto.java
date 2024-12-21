package com.api.v2.customer.dtos;

import com.api.v2.common.AddressDto;
import com.api.v2.people.dtos.PersonResponseDto;

public record CustomerResponseDto(
        PersonResponseDto personResponseDto,
        AddressDto addressDto
) {
}
