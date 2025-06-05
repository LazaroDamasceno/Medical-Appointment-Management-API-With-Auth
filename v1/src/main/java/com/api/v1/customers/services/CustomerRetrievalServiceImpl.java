package com.api.v1.customers.services;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.Customer;
import com.api.v1.customers.controllers.CustomerController;
import com.api.v1.customers.domain.CustomerCrudRepository;
import com.api.v1.customers.CustomerResponseDTO;
import com.api.v1.customers.CustomerFinder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        responseDto.add(
                linkTo(methodOn(CustomerController.class).findById(id)).withSelfRel()
        );
        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<Page<CustomerResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable).map(Customer::toDto));
    }
}
