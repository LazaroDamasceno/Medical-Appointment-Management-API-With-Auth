package com.api.v1.customers.services;

import com.api.v1.common.ObjectId;
import com.api.v1.common.Result;
import com.api.v1.common.StatusCodes;
import com.api.v1.customers.CustomerFinder;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.dtos.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerRepository repository;
    private final CustomerFinder finder;

    @Override
    public ResponseEntity<Result<Customer, CustomerResponseDto>> findById(@ObjectId String id) {
        Result<Customer, CustomerResponseDto> foundCustomer = finder.findById(id);
        if (foundCustomer.getStatusCode() == StatusCodes.NOT_FOUND) {
            return ResponseEntity.status(foundCustomer.getStatusCode()).body(foundCustomer);
        }
        return ResponseEntity.ok(foundCustomer);
    }

    @Override
    public ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(Customer::toDto));
    }
}
