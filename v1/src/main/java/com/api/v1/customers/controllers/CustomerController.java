package com.api.v1.customers.controllers;

import com.api.v1.common.MongoId;
import com.api.v1.common.Result;
import com.api.v1.customers.dtos.CustomerResponseDto;
import com.api.v1.customers.services.CustomerRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerRetrievalService retrievalService;

    @GetMapping("{id}")
    public ResponseEntity<Result<CustomerResponseDto>> findById(@MongoId @PathVariable String id) {
        return retrievalService.findById(id);
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable) {
        return retrievalService.findAll(pageable);
    }
}
