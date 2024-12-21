package com.api.v2.customers.services;

import com.api.v2.customers.domain.Customer;
import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.customers.dtos.CustomerRegistrationDto;
import com.api.v2.customers.dtos.CustomerResponseDto;
import com.api.v2.customers.utils.CustomerResponseMapper;
import com.api.v2.people.events.PersonRegistrationEventPublisher;
import com.api.v2.people.exceptions.DuplicatedEmailException;
import com.api.v2.people.exceptions.DuplicatedSsnException;
import com.api.v2.people.services.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {

    private final CustomerRepository customerRepository;
    private final PersonRegistrationEventPublisher personRegistrationEventPublisher;

    public CustomerRegistrationServiceImpl(
            CustomerRepository customerRepository,
            PersonRegistrationService personRegistrationService,
            PersonRegistrationEventPublisher personRegistrationEventPublisher
    ) {
        this.customerRepository = customerRepository;
        this.personRegistrationEventPublisher = personRegistrationEventPublisher;
    }

    @Override
    public Mono<CustomerResponseDto> register(@Valid CustomerRegistrationDto registrationDto) {
        return onDuplicatedSsn(registrationDto.personRegistrationDto().ssn())
                .then(onDuplicatedEmail(registrationDto.personRegistrationDto().email()))
                .then(Mono.defer(() -> {
                    return personRegistrationEventPublisher
                            .publish(registrationDto.personRegistrationDto())
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
