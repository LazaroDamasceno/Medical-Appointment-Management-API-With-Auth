package com.api.v2.services.impl;

import com.api.v2.domain.Customer;
import com.api.v2.domain.CustomerRepository;
import com.api.v2.dtos.CustomerRegistrationDto;
import com.api.v2.dtos.CustomerResponseDto;
import com.api.v2.exceptions.DuplicatedEmailException;
import com.api.v2.exceptions.DuplicatedSsnException;
import com.api.v2.services.CustomerRegistrationService;
import com.api.v2.services.PersonRegistrationService;
import com.api.v2.utils.CustomerResponseMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final PersonRegistrationService personRegistrationService;

    public CustomerRegistrationServiceImpl(
            CustomerRepository customerRepository,
            PersonRegistrationService personRegistrationService
    ) {
        this.customerRepository = customerRepository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public Mono<CustomerResponseDto> register(@Valid CustomerRegistrationDto registrationDto) {
        return onDuplicatedSsn(registrationDto.personRegistrationDto().ssn())
                .then(onDuplicatedEmail(registrationDto.personRegistrationDto().email()))
                .then(Mono.defer(() -> {
                    return personRegistrationService
                            .register(registrationDto.personRegistrationDto())
                            .flatMap(person ->  {
                                Customer customer  = Customer.create(registrationDto.addressDto(), person);
                                return customerRepository.save(customer);
                            })
                            .flatMap(CustomerResponseMapper::mapToMono);
                }));
    }

    private Mono<Void>  onDuplicatedSsn(String ssn) {
        return customerRepository
                .findAll()
                .filter(c -> c.getPerson().getSsn().equals(ssn))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) return Mono.error(DuplicatedSsnException::new);
                    return Mono.empty();
                });
    }

    private Mono<Void>  onDuplicatedEmail(String email) {
        return customerRepository
                .findAll()
                .filter(c -> c.getPerson().getEmail().equals(email))
                .hasElements()
                .flatMap(exists -> {
                    if (exists) return Mono.error(DuplicatedEmailException::new);
                    return Mono.empty();
                });
    }
}
