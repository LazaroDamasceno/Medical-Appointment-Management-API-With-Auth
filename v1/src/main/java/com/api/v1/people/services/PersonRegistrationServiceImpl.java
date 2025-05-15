package com.api.v1.people.services;

import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.domain.PersonRepository;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonRepository repository;

    public PersonRegistrationServiceImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public Person register(@Valid PersonRegistrationDto registrationDto) {
        return repository
                .findBySinOrEmail(registrationDto.sin(), registrationDto.email())
                .orElseGet(() -> {
                    Person person = Person.of(registrationDto);
                    return repository.save(person);
                });
    }
}
