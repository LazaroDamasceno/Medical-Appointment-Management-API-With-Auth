package com.api.v2.customer.services;

import com.api.v2.customer.dtos.CustomerModificationDto;
import reactor.core.publisher.Mono;

public interface CustomerModificationService {
    Mono<Void> modify(String ssn, CustomerModificationDto modificationDto);
}
