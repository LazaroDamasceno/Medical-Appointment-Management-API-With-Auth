package com.api.v2.customers.services;

import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import reactor.core.publisher.Mono;

public interface CustomerRegistrationService {
    Mono<CustomerResponseDto> register(CustomerRegistrationDto registrationDto);
}
