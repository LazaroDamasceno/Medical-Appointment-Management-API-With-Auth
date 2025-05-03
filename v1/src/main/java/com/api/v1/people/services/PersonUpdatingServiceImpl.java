package com.api.v1.people.services;

import com.api.v1.people.domain.PersonAuditTrail;
import com.api.v1.people.domain.PersonAuditTrailRepository;
import com.api.v1.people.domain.PersonRepository;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonUpdatingServiceImpl implements PersonUpdatingService {

    private final PersonRepository personRepository;
    private final PersonAuditTrailRepository personAuditTrailRepository;

    @Override
    public Mono<Person> update(@NotNull Person oldPerson, @Valid PersonUpdatingDto updatingDto) {
        return Mono.defer(() -> {
            PersonAuditTrail personAuditTrail = PersonAuditTrail.of(oldPerson);
            return personAuditTrailRepository
                    .save(personAuditTrail)
                    .flatMap(_ -> {
                        Person updatedPerson = Person.of(updatingDto, oldPerson.ssn());
                        return personRepository
                                .save(updatedPerson)
                                .flatMap(_ -> personRepository.delete(oldPerson))
                                .flatMap(_ -> Mono.just(updatedPerson));
                    });
        });
    }
}
