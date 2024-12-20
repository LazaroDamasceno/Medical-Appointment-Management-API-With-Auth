package com.api.v2.services;

import com.api.v2.dtos.CustomerRegistrationDto;
import com.api.v2.dtos.CustomerResponseDto;
import reactor.core.publisher.Mono;

public interface CustomerRegistrationService {
    Mono<CustomerResponseDto> register(CustomerRegistrationDto registrationDto);
}
