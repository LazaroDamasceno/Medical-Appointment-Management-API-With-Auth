package com.api.v2.customers.services;

import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.exceptions.NonExistentCustomerException;
import com.api.v2.customers.utils.CustomerResponseMapper;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerRepository customerRepository;

    public CustomerRetrievalServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Mono<CustomerResponseDto> findById(String id) {
        return customerRepository
                .findById(new ObjectId(id))
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        String ssn = optional.get().getPerson().getSsn();
                        return Mono.error(new NonExistentCustomerException(ssn));
                    }
                    return Mono.just(optional.get()).flatMap(CustomerResponseMapper::mapToMono);
                });
    }

    @Override
    public Flux<CustomerResponseDto> findAll() {
        return customerRepository
                .findAll()
                .flatMap(CustomerResponseMapper::mapToMono);
    }
}
