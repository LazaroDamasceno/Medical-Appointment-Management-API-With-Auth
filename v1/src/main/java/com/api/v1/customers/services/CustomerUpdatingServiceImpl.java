package com.api.v1.customers.services;

import com.api.v1.common.*;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerAuditTrail;
import com.api.v1.customers.domain.CustomerAuditTrailRepository;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerUpdatingServiceImpl implements CustomerUpdatingService {

    private final CustomerRepository customerRepository;
    private final CustomerAuditTrailRepository auditTrailRepository;
    private final PersonUpdatingService personUpdatingService;

    public CustomerUpdatingServiceImpl(CustomerRepository customerRepository,
                                       CustomerAuditTrailRepository auditTrailRepository,
                                       PersonUpdatingService personUpdatingService
    ) {
        this.customerRepository = customerRepository;
        this.auditTrailRepository = auditTrailRepository;
        this.personUpdatingService = personUpdatingService;
    }

    @Override
    public ResponseEntity<Result<CustomerResponseDto>> update(@ObjectId String customerId,
                                                              @Valid PersonUpdatingDto personUpdatingDto
    ) {
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        if (foundCustomer.isEmpty()) {
            Result<CustomerResponseDto> error = Result.error(ErrorMessages.CUSTOMER_NOT_FOUND.value());
            return ResponseEntity.status(StatusCode.NOT_FOUND.code()).body(error);
        }
        Customer customer = foundCustomer.get();
        CustomerAuditTrail auditTrail = CustomerAuditTrail.of(customer);
        CustomerAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = personUpdatingService.update(customer.person(), personUpdatingDto);
        customer.update(updatedPerson);
        Customer updatedCustomer = customerRepository.save(customer);
        CustomerResponseDto responseDto = updatedCustomer.toDto();
        Result<CustomerResponseDto> empty = Result.success(responseDto);
        return ResponseEntity.ok(empty);
    }
}
