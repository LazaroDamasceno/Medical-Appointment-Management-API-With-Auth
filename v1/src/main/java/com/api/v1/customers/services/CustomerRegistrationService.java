package com.api.v1.customers.services;

import com.api.v1.customers.dtos.CustomerResponseDto;
import com.api.v1.people.requests.PersonRegistrationDto;

public interface CustomerRegistrationService {
    CustomerResponseDto register(PersonRegistrationDto registrationDto);
}
