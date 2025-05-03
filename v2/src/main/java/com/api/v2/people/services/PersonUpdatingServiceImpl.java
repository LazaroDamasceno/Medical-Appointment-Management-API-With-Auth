package com.api.v2.people.services;

import com.api.v2.people.domain.PersonAuditTrail;
import com.api.v2.people.domain.PersonAuditTrailRepository;
import com.api.v2.people.domain.PersonRepository;
import com.api.v2.people.domain.exposed.Person;
import com.api.v2.people.requests.PersonUpdatingDto;
import com.api.v2.people.services.exposed.PersonUpdatingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonUpdatingServiceImpl implements PersonUpdatingService {

    private final PersonAuditTrailRepository personAuditTrailRepository;
    private final PersonRepository personRepository;

    @Override
    public Mono<Person> update(@NotNull Person oldPerson, @Valid PersonUpdatingDto updatingDto) {
        return Mono.defer(() -> {
            PersonAuditTrail auditTrail = PersonAuditTrail.of(oldPerson);
            return personAuditTrailRepository
                    .save(auditTrail)
                    .flatMap(ignored -> {
                        Person updatedPerson = Person.of(updatingDto, oldPerson.ssn());
                        return personRepository.save(updatedPerson);
                    });
        });
    }
}
