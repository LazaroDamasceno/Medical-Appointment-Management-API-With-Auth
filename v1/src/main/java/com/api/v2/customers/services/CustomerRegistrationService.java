package com.api.v1.customers.services;

import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.people.requests.PersonRegistrationDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface CustomerRegistrationService {
    Mono<ResponseEntity<CustomerResponseDto>> register(PersonRegistrationDto registrationDto);
}
