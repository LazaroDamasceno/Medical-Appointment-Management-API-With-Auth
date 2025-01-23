package com.api.v2.customers.services;

import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerFinderUtil;
import com.api.v2.customers.utils.CustomerResponseMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerRepository customerRepository;
    private final CustomerFinderUtil customerFinderUtil;

    public CustomerRetrievalServiceImpl(
            CustomerRepository customerRepository,
            CustomerFinderUtil customerFinderUtil
    ) {
        this.customerRepository = customerRepository;
        this.customerFinderUtil = customerFinderUtil;
    }

    @Override
    public Mono<CustomerResponseDto> findById(String id) {
        return customerFinderUtil
                .findById(id)
                .flatMap(CustomerResponseMapper::mapToMono);
    }

    @Override
    public Flux<CustomerResponseDto> findAll() {
        return customerRepository
                .findAll()
                .flatMap(CustomerResponseMapper::mapToMono);
    }
}
