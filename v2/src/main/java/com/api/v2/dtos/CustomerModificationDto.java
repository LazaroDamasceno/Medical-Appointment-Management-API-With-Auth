package com.api.v2.dtos;

import com.api.v2.common.AddressDto;

public record CustomerModificationDto(
        PersonModificationDto personModificationDto,
        AddressDto addressDto
) {
}
