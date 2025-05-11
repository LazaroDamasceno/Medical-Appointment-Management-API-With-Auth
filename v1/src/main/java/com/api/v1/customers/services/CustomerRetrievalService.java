package com.api.v1.customers.services;

import com.api.v1.common.PaginationResult;
import com.api.v1.customers.responses.CustomerResponseDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface CustomerRetrievalService {
    Mono<ResponseEntity<CustomerResponseDto>> findById(String customerId);
    Mono<ResponseEntity<PaginationResult<CustomerResponseDto>>> findAll(long size);
}
