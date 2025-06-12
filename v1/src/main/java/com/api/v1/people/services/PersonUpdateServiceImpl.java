package com.api.v1.people.services;

import org.springframework.stereotype.Service;

import com.api.v1.people.domain.PersonAuditTrail;
import com.api.v1.people.domain.PersonAuditRepository;
import com.api.v1.people.domain.PersonCrudRepository;
import com.api.v1.people.Person;
import com.api.v1.people.PersonUpdateDTO;
import com.api.v1.people.PersonUpdateService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class PersonUpdateServiceImpl implements PersonUpdateService {

    private final PersonCrudRepository repository;
    private final PersonAuditRepository auditTrailRepository;

    public PersonUpdateServiceImpl(PersonCrudRepository repository, PersonAuditRepository auditTrailRepository) {
        this.repository = repository;
        this.auditTrailRepository = auditTrailRepository;
    }

    @Override
    public Person update(@NotNull Person person, @Valid PersonUpdateDTO updatingDto) {
        PersonAuditTrail auditTrail = PersonAuditTrail.of(person);
        PersonAuditTrail savedAuditTrail = auditTrailRepository.save(auditTrail);
        Person updatedPerson = person.update(updatingDto);
        return repository.save(updatedPerson);
    }
}
