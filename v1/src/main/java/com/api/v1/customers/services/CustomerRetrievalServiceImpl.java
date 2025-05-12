package com.api.v1.customers.services;

import com.api.v1.common.Result;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.dtos.CustomerResponseDto;
import com.api.v1.customers.utils.CustomerFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerRepository repository;
    private final CustomerFinder customerFinder;

    @Override
    public ResponseEntity<Result<CustomerResponseDto>> findById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(Customer::toDto));
    }
}
