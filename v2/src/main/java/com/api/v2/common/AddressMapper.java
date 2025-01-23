package com.api.v2.common;

public class AddressMapper {
    public static String map(Address dto) {
        return "%s %s, %s, %s".formatted(dto.zipcode(), dto.street(), dto.city(), dto.state());
    }
}
