package com.api.v1.customers.services;

import com.api.v1.common.*;
import com.api.v1.customers.domain.Customer;
import com.api.v1.customers.domain.CustomerAuditTrail;
import com.api.v1.customers.domain.CustomerAuditTrailRepository;
import com.api.v1.customers.domain.CustomerRepository;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerUpdatingServiceImpl implements CustomerUpdatingService {

    private final CustomerRepository customerRepository;
    private final CustomerAuditTrailRepository auditTrailRepository;
    private final PersonUpdatingService personUpdatingService;

    @Override
    public ResponseEntity<Result<Void>> update(@ObjectId String customerId,
                                              @Valid PersonUpdatingDto personUpdatingDto
    ) {
        Optional<Customer> foundCustomer = customerRepository.findById(customerId);
        if (foundCustomer.isEmpty()) {
            Result<Void> error = Result.error(ErrorMessages.customerNotFound());
            return ResponseEntity.status(StatusCodes.NOT_FOUND).body(error);
        }
        Customer customer = foundCustomer.get();
        CustomerAuditTrail auditTrail = CustomerAuditTrail.of(customer);
        CustomerAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = personUpdatingService.update(customer.getPerson(), personUpdatingDto);
        customer.update(updatedPerson);
        Customer updatedCustomer = customerRepository.save(customer);
        Result<Void> empty = Result.empty();
        return ResponseEntity.ok(empty);
    }
}
