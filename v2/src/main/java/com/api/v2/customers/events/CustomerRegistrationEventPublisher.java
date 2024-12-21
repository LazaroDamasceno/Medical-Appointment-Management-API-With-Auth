package com.api.v2.customers.events;

import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.services.CustomerRegistrationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerRegistrationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final CustomerRegistrationService registrationService;

    public CustomerRegistrationEventPublisher(
            ApplicationEventPublisher eventPublisher,
            CustomerRegistrationService registrationService
    ) {
        this.eventPublisher = eventPublisher;
        this.registrationService = registrationService;
    }

    public Mono<CustomerResponseDto> publish(CustomerRegistrationDto registrationDto) {
        return registrationService
                .register(registrationDto)
                .flatMap(customerDto -> {
                    eventPublisher.publishEvent(customerDto);
                    return Mono.just(customerDto);
                });
    }
}
