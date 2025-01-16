package com.api.v2.common;

public class AddressDtoMapper {
    public static String map(AddressDto dto) {
        return "%s %s, %s, %s".formatted(dto.zipcode(), dto.street(), dto.city(), dto.state());
    }
}
