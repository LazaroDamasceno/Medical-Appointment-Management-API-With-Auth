package com.api.v1.customers.services;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.controllers.CustomerController;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.dtos.CustomerResponseDto;
import com.api.v1.customers.utils.CustomerFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class CustomerRetrievalServiceImpl implements CustomerRetrievalService {

    private final CustomerRepository customerRepository;
    private final CustomerFinder customerFinder;

    @Override
    public ResponseEntity<EntityModel<CustomerResponseDto>> findById(@ObjectId String id) {
        Customer foundCustomer = customerFinder.findById(id);
        CustomerResponseDto responseDto = foundCustomer.toDto();
        EntityModel<CustomerResponseDto> entityModel = EntityModel
                .of(responseDto)
                .add(
                        linkTo(methodOn(CustomerController.class).findById(id)).withSelfRel()
                );
        return ResponseEntity.ok(entityModel);
    }

    @Override
    public ResponseEntity<Page<CustomerResponseDto>> findAll(Pageable pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        Page<CustomerResponseDto> dtoPage = customerPage.map(Customer::toDto);
        return ResponseEntity.ok(dtoPage);
    }
}
