package com.api.v2.common;

public class AddressDtoMapper {
    public static String map(AddressDto dto) {
        return "%s %s, %s, %s".formatted(dto.street(), dto.zipcode(), dto.city(), dto.state());
    }
}
