package com.api.v2.customers.events;

import com.api.v2.customers.dtos.CustomerModificationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.services.CustomerModificationService;
import com.api.v2.customers.utils.CustomerFinderUtil;
import com.api.v2.customers.utils.CustomerResponseMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerModificationEventPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final CustomerModificationService modificationService;
    private final CustomerFinderUtil customerFinderUtil;

    public CustomerModificationEventPublisher(
            ApplicationEventPublisher eventPublisher,
            CustomerModificationService modificationService,
            CustomerFinderUtil customerFinderUtil
    ) {
        this.eventPublisher = eventPublisher;
        this.modificationService = modificationService;
        this.customerFinderUtil = customerFinderUtil;
    }

    public Mono<CustomerResponseDto> publish(String ssn, CustomerModificationDto modificationDto) {
        return modificationService.modify(ssn, modificationDto)
                .zipWith(customerFinderUtil.findBySsn(ssn))
                .flatMap(tuple -> {
                    eventPublisher.publishEvent(Mono.empty());
                    return CustomerResponseMapper.mapToMono(tuple.getT2());
                });
    }
}
