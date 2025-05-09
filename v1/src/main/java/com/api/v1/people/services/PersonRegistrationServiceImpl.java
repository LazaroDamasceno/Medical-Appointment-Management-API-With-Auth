package com.api.v1.people.services;

import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.domain.PersonRepository;
import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.services.exposed.PersonRegistrationService;
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
                .find(registrationDto.ssn(), registrationDto.email())
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
