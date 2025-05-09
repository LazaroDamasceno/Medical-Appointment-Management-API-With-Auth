package com.api.v1.customers.services;

import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.responses.CustomerResponseDto;
import com.api.v1.people.exceptions.DuplicatedEmailException;
import com.api.v1.people.exceptions.DuplicatedSsnException;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final PersonRegistrationService personRegistrationService;
    private final CustomerRepository customerRepository;

    public CustomerRegistrationServiceImpl(PersonRegistrationService personRegistrationService,
                                           CustomerRepository customerRepository
    ) {
        this.personRegistrationService = personRegistrationService;
        this.customerRepository = customerRepository;
    }

    @Override
    public Mono<ResponseEntity<CustomerResponseDto>> register(@Valid PersonRegistrationDto registrationDto) {
        return validate(registrationDto)
                .then(personRegistrationService.register(registrationDto)
                .flatMap(person -> {
                    Customer customer = Customer.of(person);
                    return customerRepository
                            .save(customer)
                            .flatMap(savedCustomer -> {
                                CustomerResponseDto responseDto = savedCustomer.toDto();
                                ResponseEntity<CustomerResponseDto> responseEntity = ResponseEntity
                                        .created(URI.create("/api/v1/customers"))
                                        .body(responseDto);
                                return Mono.just(responseEntity);
                            });
                }));
    }

    private Mono<Object> validate(PersonRegistrationDto registrationDto) {
        return customerRepository
                .findBySsn(registrationDto.sin())
                .switchIfEmpty(Mono.empty())
                .flatMap(_ -> Mono.error(DuplicatedSsnException::new))
                .then(customerRepository
                        .findByEmail(registrationDto.email())
                        .switchIfEmpty(Mono.empty())
                        .flatMap(_ -> Mono.error(DuplicatedEmailException::new))
                );
    }
}
