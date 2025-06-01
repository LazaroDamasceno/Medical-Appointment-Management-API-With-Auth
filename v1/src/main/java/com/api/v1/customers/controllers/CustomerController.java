package com.api.v1.customers.controllers;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.CustomerResponseDTO;
import com.api.v1.customers.services.CustomerRegistrationService;
import com.api.v1.customers.services.CustomerRetrievalService;
import com.api.v1.customers.services.CustomerUpdateService;
import com.api.v1.people.requests.PersonRegistrationDTO;
import com.api.v1.people.requests.PersonUpdateDTO;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController  {

    private final CustomerRetrievalService retrievalService;
    private final CustomerRegistrationService registrationService;
    private final CustomerUpdateService updatingService;

    public CustomerController(CustomerRetrievalService retrievalService,
                              CustomerRegistrationService registrationService,
                              CustomerUpdateService updatingService
    ) {
        this.retrievalService = retrievalService;
        this.registrationService = registrationService;
        this.updatingService = updatingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> findById(@ObjectId @PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> findAll(@ParameterObject Pageable pageable) {
        return retrievalService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> register(@RequestBody @Valid PersonRegistrationDTO registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("/{customerId}")
    public ResponseEntity<Void> update(@ObjectId @PathVariable String customerId,
                                               @RequestBody PersonUpdateDTO personUpdateDto
    ) {
        return updatingService.update(customerId, personUpdateDto);
    }
}
