package com.api.v2.customer.services;

import com.api.v2.customer.dtos.CustomerRegistrationDto;
import com.api.v2.customer.dtos.CustomerResponseDto;
import reactor.core.publisher.Mono;

public interface CustomerRegistrationService {
    Mono<CustomerResponseDto> register(CustomerRegistrationDto registrationDto);
}
