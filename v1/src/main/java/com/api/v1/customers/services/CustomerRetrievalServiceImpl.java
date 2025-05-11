package com.api.v1.customers.services;

import com.api.v1.common.PaginationResult;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.customers.utils.CustomerFinder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Service
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerFinder customerFinder;
    private final CustomerRepository customerRepository;

    public CustomerRetrievalServiceImpl(CustomerFinder customerFinder,
                                        CustomerRepository customerRepository
    ) {
        this.customerFinder = customerFinder;
        this.customerRepository = customerRepository;
    }

    @Override
    public Mono<ResponseEntity<CustomerResponseDto>> findById(String customerId) {
        return customerFinder
                .findById(customerId)
                .map(Customer::toDto)
                .map(ResponseEntity::ok);
    }

    @Override
    public ResponseEntity<Flux<CustomerResponseDto>> findAll(long size) {
        var flux = customerRepository
                .findAll()
                .map(Customer::toDto)
                .take(size);
        return ResponseEntity.ok(flux);
    }
}
