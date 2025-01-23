package com.api.v2.customers.services;

import com.api.v2.customers.dtos.CustomerModificationDto;
import reactor.core.publisher.Mono;

public interface CustomerModificationService {
    Mono<Void> modify(String id, CustomerModificationDto modificationDto);
}
