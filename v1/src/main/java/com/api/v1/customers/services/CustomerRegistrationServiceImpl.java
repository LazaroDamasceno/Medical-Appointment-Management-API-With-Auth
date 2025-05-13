package com.api.v1.customers.services;

import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.dtos.CustomerResponseDto;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.exceptions.DuplicatedSinException;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final PersonRegistrationService personRegistrationService;

    @Override
    public CustomerResponseDto register(@Valid PersonRegistrationDto registrationDto) {
        validate(registrationDto);
        Person savedPerson = personRegistrationService.register(registrationDto);
        Customer newCustomer = Customer.of(savedPerson);
        Customer savedCustomer = customerRepository.save(newCustomer);
        return savedCustomer.toDto();
    }

    private void validate(PersonRegistrationDto registrationDto) {
        if (customerRepository.findBySin(registrationDto.sin()).isPresent()) {
            throw new DuplicatedSinException();
        }
        if (customerRepository.findByEmail(registrationDto.email()).isPresent()) {
            throw new DuplicatedSinException();
        }
    }
}
