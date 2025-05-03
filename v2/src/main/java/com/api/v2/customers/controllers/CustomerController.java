package com.api.v2.customers.controllers;

import com.api.v2.customers.responses.CustomerResponseDto;
import com.api.v2.customers.services.CustomerRegistrationService;
import com.api.v2.people.requests.PersonRegistrationDto;
import com.api.v2.people.requests.PersonUpdatingDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRegistrationService registrationService;

    @PostMapping
    public Mono<ResponseEntity<CustomerResponseDto>> register(@Valid @RequestBody PersonRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }
}
