package com.api.v1.customers.services;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerAuditTrail;
import com.api.v1.customers.domain.CustomerAuditRepository;
import com.api.v1.customers.domain.CustomerCrudRepository;
import com.api.v1.customers.utils.exposed.CustomerFinder;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDTO;
import com.api.v1.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerUpdatingServiceImpl implements CustomerUpdatingService {

    private final CustomerFinder finder;
    private final CustomerCrudRepository repository;
    private final CustomerAuditRepository auditTrailRepository;
    private final PersonUpdatingService personUpdatingService;

    public CustomerUpdatingServiceImpl(CustomerFinder finder,
                                       CustomerCrudRepository repository,
                                       CustomerAuditRepository auditTrailRepository,
                                       PersonUpdatingService personUpdatingService
    ) {
        this.finder = finder;
        this.repository = repository;
        this.auditTrailRepository = auditTrailRepository;
        this.personUpdatingService = personUpdatingService;
    }

    @Override
    public ResponseEntity<Void> update(@ObjectId String customerId, @Valid PersonUpdatingDTO personUpdatingDTO) {
        Customer foundCustomer = finder.findById(customerId);
        CustomerAuditTrail auditTrail = CustomerAuditTrail.of(foundCustomer);
        CustomerAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = personUpdatingService.update(foundCustomer.getPerson(), personUpdatingDTO);
        foundCustomer.update(updatedPerson);
        Customer updatedCustomer = repository.save(foundCustomer);
        return ResponseEntity.noContent().build();
    }
}
