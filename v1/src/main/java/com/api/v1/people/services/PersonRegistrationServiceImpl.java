package com.api.v1.people.services;

import com.api.v1.people.domain.Person;
import com.api.v1.people.domain.PersonCrudRepository;
import com.api.v1.people.requests.PersonRegistrationDTO;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonCrudRepository repository;

    public PersonRegistrationServiceImpl(PersonCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person register(@Valid PersonRegistrationDTO registrationDto) {
        return repository
                .findBySinOrEmail(registrationDto.sin(), registrationDto.email())
                .orElseGet(() -> {
                    Person person = Person.of(registrationDto);
                    return repository.save(person);
                });
    }
}
