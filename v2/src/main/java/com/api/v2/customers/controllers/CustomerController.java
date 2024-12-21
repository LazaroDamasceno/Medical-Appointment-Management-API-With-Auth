package com.api.v2.customers.controllers;

import com.api.v2.customers.dtos.CustomerModificationDto;
import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.events.CustomerModificationEventPublisher;
import com.api.v2.customers.events.CustomerRegistrationEventPublisher;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
public class CustomerController {

    private final CustomerRegistrationEventPublisher registrationEventPublisher;
    private final CustomerModificationEventPublisher modificationEventPublisher;

    public CustomerController(
            CustomerRegistrationEventPublisher registrationEventPublisher,
            CustomerModificationEventPublisher modificationEventPublisher
    ) {
        this.registrationEventPublisher = registrationEventPublisher;
        this.modificationEventPublisher = modificationEventPublisher;
    }

    @PostMapping
    public Mono<CustomerResponseDto> register(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        return registrationEventPublisher.publish(registrationDto);
    }

    @PatchMapping("{ssn}")
    public Mono<CustomerResponseDto> modify(@PathVariable String ssn, @Valid @RequestBody CustomerModificationDto modificationDto) {
        return modificationEventPublisher.publish(ssn, modificationDto);
    }
}
