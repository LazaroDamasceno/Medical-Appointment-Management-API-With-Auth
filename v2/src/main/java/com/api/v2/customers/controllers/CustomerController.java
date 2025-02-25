package com.api.v2.customers.controllers;

import com.api.v2.common.Id;
import com.api.v2.customers.dtos.CustomerModificationDto;
import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.services.CustomerRegistrationService;
import com.api.v2.customers.services.CustomerRetrievalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
public class CustomerController {

    private final CustomerRegistrationService registrationService;
    private final CustomerRetrievalService retrievalService;

    public CustomerController(
            CustomerRegistrationService registrationService,
            CustomerRetrievalService retrievalService
    ) {
        this.registrationService = registrationService;
        this.retrievalService = retrievalService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<CustomerResponseDto> register(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @GetMapping("{id}")
    public Mono<CustomerResponseDto> findById(@PathVariable @Id String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public Flux<CustomerResponseDto> findAll() {
        return retrievalService.findAll();
    }
}
