package com.api.v2.customers.services;

import com.api.v2.customers.domain.CustomerAuditTrail;
import com.api.v2.customers.domain.CustomerAuditTrailRepository;
import com.api.v2.customers.domain.CustomerRepository;
import com.api.v2.customers.domain.exposed.Customer;
import com.api.v2.customers.utils.CustomerFinder;
import com.api.v2.people.requests.PersonUpdatingDto;
import com.api.v2.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerUpdatingServiceImpl implements CustomerUpdatingService {

    private final CustomerAuditTrailRepository auditTrailRepository;
    private final CustomerRepository customerRepository;
    private final PersonUpdatingService personUpdatingService;
    private final CustomerFinder customerFinder;

    @Override
    public Mono<ResponseEntity<Void>> update(String customerId, @Valid PersonUpdatingDto updatingDto) {
        return customerFinder
                .findById(customerId)
                .flatMap(foundCustomer -> {
                    CustomerAuditTrail auditTrail = CustomerAuditTrail.of(foundCustomer);
                    return auditTrailRepository
                            .save(auditTrail)
                            .then(personUpdatingService.update(foundCustomer.person(), updatingDto))
                            .flatMap(updatedPerson -> {
                                return customerRepository
                                        .delete(foundCustomer)
                                        .flatMap(_ -> {
                                            Customer customer = Customer.of(updatedPerson);
                                            return customerRepository.save(customer);
                                        });
                            });
                })
                .then(Mono.defer(() -> {
                    ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
                    return Mono.just(responseEntity);
                }));
    }
}
