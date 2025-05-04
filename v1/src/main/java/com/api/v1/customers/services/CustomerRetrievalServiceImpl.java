package com.api.v1.customers.services;

import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.customers.utils.CustomerFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerRepository customerRepository;
    private final CustomerFinder customerFinder;

    @Override
    public Mono<ResponseEntity<CustomerResponseDto>> findById(String customerId) {
        return customerFinder
                .findById(customerId)
                .map(Customer::toDto)
                .map(ResponseEntity::ok)
                .flatMap(Mono::just);
    }

    @Override
    public ResponseEntity<Flux<CustomerResponseDto>> findAll() {
        Flux<CustomerResponseDto> flux = customerRepository
                .findAll()
                .map(Customer::toDto);
        return ResponseEntity.ok(flux);
    }
}
