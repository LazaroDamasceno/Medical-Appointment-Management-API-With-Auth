package com.api.v2.services.impl;

import com.api.v2.domain.CustomerRepository;
import com.api.v2.dtos.CustomerModificationDto;
import com.api.v2.services.CustomerModificationService;
import com.api.v2.services.PersonModificationService;
import com.api.v2.utils.CustomerFinderUtil;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerModificationServiceImpl implements CustomerModificationService {

    private final CustomerRepository customerRepository;
    private final PersonModificationService personModificationService;
    private final CustomerFinderUtil customerFinderUtil;

    public CustomerModificationServiceImpl(
            CustomerRepository customerRepository,
            PersonModificationService personModificationService,
            CustomerFinderUtil customerFinderUtil
    ) {
        this.customerRepository = customerRepository;
        this.personModificationService = personModificationService;
        this.customerFinderUtil = customerFinderUtil;
    }

    @Override
    public Mono<Void> modify(String ssn, @Valid CustomerModificationDto modificationDto) {
        return customerFinderUtil
                .findBySsn(ssn)
                .flatMap(customer -> {
                    return personModificationService
                            .modify(customer.getPerson(), modificationDto.personModificationDto())
                            .flatMap(modifiedPerson -> {
                               customer.setPerson(modifiedPerson);
                               return customerRepository.save(customer);
                            });
                })
                .then();
    }
}
