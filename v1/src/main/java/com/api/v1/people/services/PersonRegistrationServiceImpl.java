package com.api.v1.people.services;

import com.api.v1.people.domain.Person;
import com.api.v1.people.domain.PersonRepository;
import com.api.v1.people.requests.PersonRegistrationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonRegistrationServiceImpl implements PersonRegistrationService {

    private final PersonRepository repository;

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
