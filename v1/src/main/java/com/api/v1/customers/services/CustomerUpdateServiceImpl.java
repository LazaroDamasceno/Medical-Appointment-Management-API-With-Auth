package com.api.v1.customers.services;

import com.api.v1.common.ObjectId;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerAuditTrail;
import com.api.v1.customers.domain.CustomerAuditRepository;
import com.api.v1.customers.domain.CustomerCrudRepository;
import com.api.v1.customers.utils.CustomerFinder;
import com.api.v1.people.domain.Person;
import com.api.v1.people.requests.PersonUpdateDTO;
import com.api.v1.people.services.exposed.PersonUpdateService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerUpdateServiceImpl implements CustomerUpdateService {

    private final CustomerFinder finder;
    private final CustomerCrudRepository repository;
    private final CustomerAuditRepository auditTrailRepository;
    private final PersonUpdateService personUpdateService;

    public CustomerUpdateServiceImpl(CustomerFinder finder,
                                     CustomerCrudRepository repository,
                                     CustomerAuditRepository auditTrailRepository,
                                     PersonUpdateService personUpdateService
    ) {
        this.finder = finder;
        this.repository = repository;
        this.auditTrailRepository = auditTrailRepository;
        this.personUpdateService = personUpdateService;
    }

    @Override
    public ResponseEntity<Void> update(@ObjectId String customerId, @Valid PersonUpdateDTO personUpdateDTO) {
        Customer foundCustomer = finder.findById(customerId);
        CustomerAuditTrail auditTrail = CustomerAuditTrail.of(foundCustomer);
        CustomerAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = personUpdateService.update(foundCustomer.getPerson(), personUpdateDTO);
        foundCustomer.update(updatedPerson);
        Customer updatedCustomer = repository.save(foundCustomer);
        return ResponseEntity.noContent().build();
    }
}
