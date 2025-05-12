package com.api.v1.customers.services;

import com.api.v1.common.Result;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.dtos.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CustomerRetrievalService {
    ResponseEntity<Result<Customer, CustomerResponseDto>> findById(String id);
    ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable);
}
