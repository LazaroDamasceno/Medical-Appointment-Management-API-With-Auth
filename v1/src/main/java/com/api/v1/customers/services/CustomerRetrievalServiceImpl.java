package com.api.v1.customers.services;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.Customer;
import com.api.v1.customers.domain.CustomerCrudRepository;
import com.api.v1.customers.CustomerResponseDTO;
import com.api.v1.customers.CustomerFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerFinder finder;
    private final CustomerCrudRepository repository;

    public CustomerRetrievalServiceImpl(CustomerFinder finder, CustomerCrudRepository repository) {
        this.finder = finder;
        this.repository = repository;
    }

    @Override
    public ResponseEntity<CustomerResponseDTO> findById(@ObjectId String id) {
        Customer foundCustomer = finder.findById(id);
        CustomerResponseDTO responseDto = foundCustomer.toDto();
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<Page<CustomerResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(Customer::toDto));
    }
}
