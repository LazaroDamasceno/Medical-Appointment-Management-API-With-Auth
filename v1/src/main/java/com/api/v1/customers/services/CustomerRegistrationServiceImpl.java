package com.api.v1.customers.services;

import com.api.v1.common.ErrorMessages;
import com.api.v1.common.Result;
import com.api.v1.common.StatusCodes;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.dtos.CustomerResponseDto;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final PersonRegistrationService personRegistrationService;

    @Override
    public ResponseEntity<Result<CustomerResponseDto>> register(@Valid PersonRegistrationDto registrationDto) {
        Optional<Customer> foundCustomerBySin = customerRepository.findBySin(registrationDto.sin());
        if (foundCustomerBySin.isPresent()) {
            Result<CustomerResponseDto> error = Result.error(ErrorMessages.duplicatedSin());
            return ResponseEntity.status(StatusCodes.CONFLICT).body(error);
        }
        Optional<Customer> foundCustomerByEmail = customerRepository.findByEmail(registrationDto.email());
        if (foundCustomerByEmail.isPresent()) {
            Result<CustomerResponseDto> error = Result.error(ErrorMessages.duplicatedEmail());
            return ResponseEntity.status(StatusCodes.CONFLICT).body(error);
        }
        Person savedPerson = personRegistrationService.register(registrationDto);
        Customer newCustomer = Customer.of(savedPerson);
        Customer savedCustomer = customerRepository.save(newCustomer);
        CustomerResponseDto responseDto = savedCustomer.toDto();
        Result<CustomerResponseDto> success = Result.success(responseDto);
        return ResponseEntity.created(URI.create("/api/v1/customers")).body(success);
    }
}
