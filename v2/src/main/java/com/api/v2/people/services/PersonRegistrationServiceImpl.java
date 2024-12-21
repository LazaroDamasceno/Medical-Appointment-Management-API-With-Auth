package com.api.v2.people.services;

import com.api.v2.people.domain.Person;
import com.api.v2.people.domain.PersonRepository;
import com.api.v2.people.dtos.PersonRegistrationDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonRepository personRepository;

    public PersonRegistrationServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Mono<Person> register(@Valid PersonRegistrationDto registrationDto) {
        return Mono.defer(() -> {
            Person person = Person.create(registrationDto);
            return personRepository.save(person);
        });
    }
}
