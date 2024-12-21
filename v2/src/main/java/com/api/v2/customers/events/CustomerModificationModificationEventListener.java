package com.api.v2.customers.events;

import com.api.v2.customers.dtos.CustomerModificationDto;
import com.api.v2.customers.services.CustomerModificationService;
import org.springframework.modulith.events.ApplicationModuleListener;
import reactor.core.publisher.Mono;

public class CustomerModificationModificationEventListener {

    private final CustomerModificationService modificationService;

    public CustomerModificationModificationEventListener(CustomerModificationService modificationService) {
        this.modificationService = modificationService;
    }

    @ApplicationModuleListener
    public Mono<Void> listen(String ssn, CustomerModificationDto modificationDto) {
        return modificationService.modify(ssn, modificationDto);
    }
}
