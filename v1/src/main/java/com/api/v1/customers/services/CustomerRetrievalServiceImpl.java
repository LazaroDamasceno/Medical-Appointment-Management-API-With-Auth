package com.api.v1.customers.services;

import com.api.v1.common.*;
import com.api.v1.customers.controllers.CustomerController;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.response.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerRepository customerRepository;

    public CustomerRetrievalServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<Result<CustomerResponseDto>> findById(@ObjectId String id) {
        Optional<Customer> foundCustomer = customerRepository.findById(id);
        if (foundCustomer.isEmpty()) {
            Result<CustomerResponseDto> error = Result.error(ErrorMessages.CUSTOMER_NOT_FOUND.value());
            return ResponseEntity.status(StatusCode.NOT_FOUND.code()).body(error);
        }
        CustomerResponseDto responseDto = foundCustomer
                .get()
                .toDto()
                .add(linkTo(methodOn(CustomerController.class).findById(id)).withSelfRel());
        Result<CustomerResponseDto> success = Result.success(responseDto);
        return ResponseEntity.ok(success);
    }

    @Override
    public ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        Page<CustomerResponseDto> dtoPage = customerPage.map(Customer::toDto);
        return ResponseEntity.ok(dtoPage);
    }
}
