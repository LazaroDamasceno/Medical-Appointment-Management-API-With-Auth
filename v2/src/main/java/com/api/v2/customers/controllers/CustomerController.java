package com.api.v2.customers.controllers;

import com.api.v2.customers.requests.CustomerRegistrationDto;
import com.api.v2.customers.responses.CustomerResponseDto;
import com.api.v2.customers.services.CustomerRegistrationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRegistrationService registrationService;

    @PostMapping
    public Mono<ResponseEntity<CustomerResponseDto>> register(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }
}
