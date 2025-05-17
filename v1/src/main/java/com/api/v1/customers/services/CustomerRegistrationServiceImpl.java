package com.api.v1.customers.services;

import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.response.CustomerResponseDto;
import com.api.v1.people.exceptions.DuplicatedEmailException;
import com.api.v1.people.exceptions.DuplicatedSINException;
import com.api.v1.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    public CustomerRegistrationServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<CustomerResponseDto> register(@Valid PersonRegistrationDto registrationDto) {
        return null;
    }

    private void validate(PersonRegistrationDto registrationDto) {
        if (customerRepository.findBySIN(registrationDto.sin()).isPresent()) {
            throw new DuplicatedSINException();
        }
        if (customerRepository.findByEmail(registrationDto.email()).isPresent()) {
            throw new DuplicatedEmailException();
        }
    }
}
