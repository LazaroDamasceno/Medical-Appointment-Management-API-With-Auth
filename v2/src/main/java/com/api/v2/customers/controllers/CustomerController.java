package com.api.v2.customers.controllers;

import com.api.v2.customers.dtos.CustomerModificationDto;
import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.services.CustomerModificationService;
import com.api.v2.customers.services.CustomerRegistrationService;
import com.api.v2.customers.services.CustomerRetrievalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
public class CustomerController {

    private final CustomerRegistrationService registrationService;
    private final CustomerModificationService modificationService;
    private final CustomerRetrievalService retrievalService;

    public CustomerController(
            CustomerRegistrationService registrationService,
            CustomerModificationService modificationService,
            CustomerRetrievalService retrievalService
    ) {
        this.registrationService = registrationService;
        this.modificationService = modificationService;
        this.retrievalService = retrievalService;
    }

    @PostMapping
    public Mono<CustomerResponseDto> register(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{ssn}")
    public Mono<Void> modify(@PathVariable String ssn, @Valid @RequestBody CustomerModificationDto modificationDto) {
        return modificationService.modify(ssn, modificationDto);
    }

    @GetMapping("{ssn}")
    public Mono<CustomerResponseDto> findBySsn(String ssn) {
        return retrievalService.findBySsn(ssn);
    }

    @GetMapping
    public Flux<CustomerResponseDto> findAll() {
        return retrievalService.findAll();
    }
}
