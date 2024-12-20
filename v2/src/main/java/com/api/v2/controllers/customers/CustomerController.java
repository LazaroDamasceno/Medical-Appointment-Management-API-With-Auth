package com.api.v2.controllers.customers;

import com.api.v2.dtos.CustomerModificationDto;
import com.api.v2.dtos.CustomerRegistrationDto;
import com.api.v2.dtos.CustomerResponseDto;
import com.api.v2.services.CustomerModificationService;
import com.api.v2.services.CustomerRegistrationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v2/customers")
public class CustomerController {

    private final CustomerRegistrationService registrationService;
    private final CustomerModificationService modificationService;

    public CustomerController(
            CustomerRegistrationService registrationService,
            CustomerModificationService modificationService
    ) {
        this.registrationService = registrationService;
        this.modificationService = modificationService;
    }


    @PostMapping
    public Mono<CustomerResponseDto> register(@Valid @RequestBody CustomerRegistrationDto registrationDto) {
        return registrationService.register(registrationDto);
    }

    @PatchMapping("{ssn}")
    public Mono<Void> modify(@PathVariable String ssn, @Valid @RequestBody CustomerModificationDto modificationDto) {
        return modificationService.modify(ssn, modificationDto);
    }
}
