package com.api.v1.customers.services;

import com.api.v1.customers.dtos.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

public interface CustomerRetrievalService {
    ResponseEntity<CustomerResponseDto> findById(String id);
    ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable);
}
