package com.api.v1.customers.services;

import com.api.v1.customers.domain.CustomerAuditTrail;
import com.api.v1.customers.domain.CustomerAuditTrailRepository;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.domain.exposed.Customer;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.PersonUpdatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerUpdatingServiceImpl implements CustomerUpdatingService {

    private final CustomerRepository customerRepository;
    private final CustomerAuditTrailRepository customerAuditTrailRepository;
    private final PersonUpdatingService personUpdatingService;
    private final CustomerFinder customerFinder;

    @Override
    public Mono<Void> update(String customerId, @Valid PersonUpdatingDto updatingDto) {
        return customerFinder
                .findById(customerId)
                .flatMap(foundCustomer -> {
                    return personUpdatingService
                            .update(foundCustomer.person(), updatingDto)
                            .flatMap(updatedPerson -> {
                                Mono<Void> deletedFoundCustomer = customerRepository.delete(foundCustomer);
                                CustomerAuditTrail customerAuditTrail = CustomerAuditTrail.of(foundCustomer);
                                Mono<CustomerAuditTrail> savedAuditTrail = customerAuditTrailRepository.save(customerAuditTrail);
                                Customer updatedCustomer = Customer.of(updatedPerson);
                                return customerRepository.save(updatedCustomer);
                            });
                })
                .then();
    }

}
