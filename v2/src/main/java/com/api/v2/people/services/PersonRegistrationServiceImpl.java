package com.api.v2.people.services;

import com.api.v2.people.domain.exposed.Person;
import com.api.v2.people.domain.PersonRepository;
import com.api.v2.people.requests.PersonRegistrationDto;
import com.api.v2.people.services.exposed.PersonRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public abstract class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonRepository personRepository;

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
