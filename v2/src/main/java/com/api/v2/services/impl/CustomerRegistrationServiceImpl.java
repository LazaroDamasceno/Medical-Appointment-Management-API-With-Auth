package com.api.v2.services.impl;

import com.api.v2.domain.Customer;
import com.api.v2.domain.CustomerRepository;
import com.api.v2.dtos.CustomerRegistrationDto;
import com.api.v2.dtos.CustomerResponseDto;
import com.api.v2.services.CustomerRegistrationService;
import com.api.v2.services.PersonRegistrationService;
import com.api.v2.utils.CustomerResponseMapper;
import com.api.v2.utils.PersonDuplicatedDataVerifierUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final PersonDuplicatedDataVerifierUtil verifierUtil;
    private final CustomerRepository customerRepository;
    private final PersonRegistrationService personRegistrationService;

    public CustomerRegistrationServiceImpl(
            PersonDuplicatedDataVerifierUtil verifierUtil,
            CustomerRepository customerRepository,
            PersonRegistrationService personRegistrationService
    ) {
        this.verifierUtil = verifierUtil;
        this.customerRepository = customerRepository;
        this.personRegistrationService = personRegistrationService;
    }

    @Override
    public Mono<CustomerResponseDto> register(@Valid CustomerRegistrationDto registrationDto) {
        return verifierUtil
                .onDuplicatedEmail(registrationDto.personRegistrationDto().email())
                .then(verifierUtil.onDuplicatedSsn(registrationDto.personRegistrationDto().ssn()))
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
}
