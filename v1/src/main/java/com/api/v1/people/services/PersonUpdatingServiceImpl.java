package com.api.v1.people.services;

import com.api.v1.people.domain.PersonAuditTrail;
import com.api.v1.people.domain.PersonAuditTrailRepository;
import com.api.v1.people.domain.PersonRepository;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.exposed.PersonUpdatingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonUpdatingServiceImpl implements PersonUpdatingService {

    private final PersonRepository personRepository;
    private final PersonAuditTrailRepository auditTrailRepository;

    public PersonUpdatingServiceImpl(PersonRepository personRepository,
                                     PersonAuditTrailRepository auditTrailRepository
    ) {
        this.personRepository = personRepository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public Mono<Person> update(Person currentPerson, PersonUpdatingDto updatingDto) {
        return Mono.defer(() -> {
            PersonAuditTrail auditTrail = PersonAuditTrail.of(currentPerson);
            return auditTrailRepository
                    .save(auditTrail)
                    .flatMap(_ -> {
                        currentPerson.update(updatingDto);
                        return personRepository.save(currentPerson);
                    });
        });
    }
}
