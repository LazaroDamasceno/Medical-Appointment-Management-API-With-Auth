package com.api.v1.customers.services;

import com.api.v1.customers.response.CustomerResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CustomerRetrievalService {
    ResponseEntity<CustomerResponseDTO> findById(String id);
    ResponseEntity<Page<CustomerResponseDTO>> findAll(Pageable pageable);
}
