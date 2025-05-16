package com.api.v1.customers.services;

import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.people.requests.PersonRegistrationDto;
import org.springframework.http.ResponseEntity;

public interface CustomerRegistrationService {
    ResponseEntity<CustomerResponseDto> register(PersonRegistrationDto registrationDto);
}
