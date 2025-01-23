package com.api.v2.customers.services;

import com.api.v2.customers.dtos.CustomerResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRetrievalService {
    Mono<CustomerResponseDto> findById(String id);
    Flux<CustomerResponseDto> findAll();
}
