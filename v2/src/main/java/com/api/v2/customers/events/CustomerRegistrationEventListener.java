package com.api.v2.customers.events;

import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.services.CustomerRegistrationService;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class CustomerRegistrationEventListener {

    private final CustomerRegistrationService registrationService;

    public CustomerRegistrationEventListener(CustomerRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @ApplicationModuleListener
    public Mono<Void> listen(CustomerRegistrationDto registrationDto) {
        return registrationService
                .register(registrationDto)
                .then();
    }
}
