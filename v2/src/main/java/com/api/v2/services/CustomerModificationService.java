package com.api.v2.services;

import com.api.v2.dtos.CustomerModificationDto;
import reactor.core.publisher.Mono;

public interface CustomerModificationService {
    Mono<Void> modify(String ssn, CustomerModificationDto modificationDto);
}
