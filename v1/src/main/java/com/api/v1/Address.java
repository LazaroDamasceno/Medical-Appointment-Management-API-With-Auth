package com.api.v1;

public record Address(
        String street,
        String city,
        String region,
        String zipcode
) {
}
