package com.api.v1.customers.services;

import com.api.v1.customers.responses.CustomerResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRetrievalService {
    Mono<ResponseEntity<CustomerResponseDto>> findById(String customerId);
    Flux<CustomerResponseDto> findAll();
}
