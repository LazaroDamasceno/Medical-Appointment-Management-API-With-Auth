package com.api.v1.customers.controllers;

import com.api.v1.customers.controllers.exposed.CustomerController;
import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.customers.services.CustomerRegistrationService;
import com.api.v1.customers.services.CustomerRetrievalService;
import com.api.v1.customers.services.CustomerUpdatingService;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.requests.PersonUpdatingDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerControllerImpl extends CustomerController {

    @PostMapping
    @Operation(summary = "Register a new customer")
    public Mono<ResponseEntity<CustomerResponseDto>> register(@Valid @RequestBody PersonRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{customerId}")
    @Operation(summary = "Update a customer")
    public Mono<ResponseEntity<Void>> update(@PathVariable String customerId, @Valid @RequestBody PersonUpdatingDto updatingDto) {
        return updatingService.update(customerId, updatingDto);
    }

    @GetMapping("{customerId}")
    @Operation(summary = "Find a customer by id")
    public Mono<ResponseEntity<CustomerResponseDto>> findById(@PathVariable String customerId) {
        return retrievalService.findById(customerId);
    }

    @GetMapping
    @Operation(summary = "Find all customers")
    public ResponseEntity<Flux<CustomerResponseDto>> findAll() {
        return retrievalService.findAll();
    }
}
