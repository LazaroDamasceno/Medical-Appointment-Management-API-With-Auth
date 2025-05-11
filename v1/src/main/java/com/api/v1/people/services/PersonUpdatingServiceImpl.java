package com.api.v1.people.services;

import com.api.v1.people.domain.Person;
import com.api.v1.people.domain.PersonAuditTrail;
import com.api.v1.people.domain.PersonAuditTrailRepository;
import com.api.v1.people.domain.PersonRepository;
import com.api.v1.people.requests.PersonUpdatingDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonUpdatingServiceImpl implements PersonUpdatingService {

    private final PersonRepository repository;
    private final PersonAuditTrailRepository auditTrailRepository;

    @Override
    public Person update(@NotNull Person person, @Valid PersonUpdatingDto updatingDto) {
        PersonAuditTrail auditTrail = PersonAuditTrail.of(person);
        PersonAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = Person.from(updatingDto, person.sin());
        Person savedUpdatedPerson = repository.save(updatedPerson);
        repository.delete(person);
        return savedUpdatedPerson;
    }
}
