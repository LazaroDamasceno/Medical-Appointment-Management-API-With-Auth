package com.api.v1.people.services;

import org.springframework.stereotype.Service;

import com.api.v1.people.domain.PersonAuditTrail;
import com.api.v1.people.domain.PersonAuditTrailRepository;
import com.api.v1.people.domain.PersonRepository;
import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDto;
import com.api.v1.people.services.exposed.PersonUpdatingService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class PersonUpdatingServiceImpl implements PersonUpdatingService {

    private final PersonRepository repository;
    private final PersonAuditTrailRepository auditTrailRepository;

    public PersonUpdatingServiceImpl(PersonRepository repository, PersonAuditTrailRepository auditTrailRepository) {
        this.repository = repository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public Person update(@NotNull Person person, @Valid PersonUpdatingDto updatingDto) {
        PersonAuditTrail auditTrail = PersonAuditTrail.of(person);
        PersonAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        person.update(updatingDto);
        return repository.save(person);
    }
}
