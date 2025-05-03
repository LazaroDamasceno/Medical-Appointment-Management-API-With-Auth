package com.api.v1.customers.services;

import com.api.v1.customers.domain.CustomerAuditTrail;
import com.api.v1.customers.domain.CustomerAuditTrailRepository;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerUpdatingServiceImpl implements CustomerUpdatingService {

    private final PersonUpdatingService personUpdatingService;
    private final CustomerRepository customerRepository;
    private final CustomerAuditTrailRepository auditTrailRepository;
    private final CustomerFinder customerFinder;

    @Override
    public Mono<ResponseEntity<Void>> update(String customerId, @Valid PersonUpdatingDto updatingDto) {
        return customerFinder
                .findById(customerId)
                .flatMap(foundCustomer -> {
                    return personUpdatingService
                            .update(foundCustomer.getPerson(), updatingDto)
                            .flatMap(updatedPerson -> {
                                CustomerAuditTrail auditTrail = CustomerAuditTrail.of(foundCustomer);
                                return auditTrailRepository
                                        .save(auditTrail)
                                        .flatMap(_ -> {
                                            foundCustomer.update(updatedPerson);
                                            return customerRepository.save(foundCustomer);
                                        });
                            });
                })
                .flatMap(_ -> {
                    ResponseEntity<Void> responseEntity = ResponseEntity
                            .noContent()
                            .build();
                    return Mono.just(responseEntity);
                });
    }

}
