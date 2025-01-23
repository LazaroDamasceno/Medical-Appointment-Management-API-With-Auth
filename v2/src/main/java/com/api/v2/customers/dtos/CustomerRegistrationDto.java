package com.api.v2.customers.dtos;

import com.api.v2.common.Address;
import com.api.v2.people.dtos.PersonRegistrationDto;

public record CustomerRegistrationDto(
        PersonRegistrationDto person,
        Address address
) {
}
