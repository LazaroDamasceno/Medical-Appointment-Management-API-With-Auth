package com.api.v1.customers.services;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.customers.utils.exposed.CustomerFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerFinder finder;
    private final CustomerRepository repository;

    public CustomerRetrievalServiceImpl(CustomerFinder finder, CustomerRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    @Override
    public ResponseEntity<CustomerResponseDto> findById(@ObjectId String id) {
        Customer foundCustomer = finder.findById(id);
        CustomerResponseDto responseDto = foundCustomer.toDto();
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(Customer::toDto));
    }
}
