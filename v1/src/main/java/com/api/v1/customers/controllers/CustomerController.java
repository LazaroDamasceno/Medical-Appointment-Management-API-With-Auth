package com.api.v1.customers.controllers;

import com.api.v1.common.ObjectId;
import com.api.v1.common.Result;
import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.customers.services.CustomerRegistrationService;
import com.api.v1.customers.services.CustomerRetrievalService;
import com.api.v1.customers.services.CustomerUpdatingService;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.requests.PersonUpdatingDto;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController  {

    private final CustomerRetrievalService retrievalService;
    private final CustomerRegistrationService registrationService;
    private final CustomerUpdatingService updatingService;

    public CustomerController(CustomerRetrievalService retrievalService,
                              CustomerRegistrationService registrationService,
                              CustomerUpdatingService updatingService
    ) {
        this.retrievalService = retrievalService;
        this.registrationService = registrationService;
        this.updatingService = updatingService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Result<CustomerResponseDto>> findById(@ObjectId @PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDto>> findAll(@ParameterObject Pageable pageable) {
        return retrievalService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Result<CustomerResponseDto>> register(@RequestBody @Valid PersonRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{customerId}")
    public ResponseEntity<Result<Void>> update(@ObjectId @PathVariable String customerId,
                                               @RequestBody PersonUpdatingDto personUpdatingDto
    ) {
        return updatingService.update(customerId, personUpdatingDto);
    }
}
