package com.api.v1.customers.services;

import com.api.v1.common.ErrorMessages;
import com.api.v1.common.Result;
import com.api.v1.common.StatusCode;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final PersonRegistrationService personRegistrationService;

    public CustomerRegistrationServiceImpl(CustomerRepository customerRepository,
                                           PersonRegistrationService personRegistrationService
    ) {
        this.customerRepository = customerRepository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public ResponseEntity<Result<CustomerResponseDto>> register(@Valid PersonRegistrationDto registrationDto) {
        Optional<Customer> foundCustomerBySIN = customerRepository.findBySIN(registrationDto.sin());
        if (foundCustomerBySIN.isPresent()) {
            Result<CustomerResponseDto> error = Result.error(ErrorMessages.DUPLICATED_SIN.value());
            return ResponseEntity.status(StatusCode.CONFLICT.code()).body(error);
        }
        Optional<Customer> foundCustomerByEmail = customerRepository.findByEmail(registrationDto.email());
        if (foundCustomerByEmail.isPresent()) {
            Result<CustomerResponseDto> error = Result.error(ErrorMessages.DUPLICATED_EMAIL.value());
            return ResponseEntity.status(StatusCode.CONFLICT.code()).body(error);
        }
        Person savedPerson = personRegistrationService.register(registrationDto);
        Customer newCustomer = Customer.of(savedPerson);
        Customer savedCustomer = customerRepository.save(newCustomer);
        CustomerResponseDto responseDto = savedCustomer.toDto();
        Result<CustomerResponseDto> success = Result.created(responseDto);
        return ResponseEntity.created(URI.create("/api/v1/customers")).body(success);
    }
}
