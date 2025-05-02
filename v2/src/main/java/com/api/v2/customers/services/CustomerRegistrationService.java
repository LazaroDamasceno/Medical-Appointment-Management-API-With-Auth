package com.api.v2.customers.services;

import com.api.v2.customers.responses.CustomerResponseDto;
import com.api.v2.people.requests.PersonRegistrationDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface CustomerRegistrationService {
    Mono<ResponseEntity<CustomerResponseDto>> register(PersonRegistrationDto registrationDto);
}
