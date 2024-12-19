package com.api.v2.common;

public record AddressDto(
        String state,
        String city,
        String street,
        String zipcode
) {
}
