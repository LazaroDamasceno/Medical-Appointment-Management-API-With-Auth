package com.api.v2.people.services.impl;

import com.api.v2.people.domain.Person;
import com.api.v2.people.domain.PersonRepository;
import com.api.v2.people.dtos.PersonRegistrationDto;
import com.api.v2.people.services.interfaces.PersonRegistrationService;
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
        return personRepository
                .findAll()
                .filter(p -> p.getSsn().equals(registrationDto.ssn()))
                .singleOrEmpty()
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isPresent()) {
                        return Mono.just(optional.get());
                    }
                    Person person = Person.of(registrationDto);
                    return personRepository.save(person);
                });
    }
}
