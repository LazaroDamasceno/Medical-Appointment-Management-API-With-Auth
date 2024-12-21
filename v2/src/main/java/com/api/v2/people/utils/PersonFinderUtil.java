package com.api.v2.people.utils;

import com.api.v2.people.domain.Person;
import com.api.v2.people.domain.PersonRepository;
import com.api.v2.people.exceptions.NonExistentSsnException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonFinderUtil {

    private final PersonRepository personRepository;

    public PersonFinderUtil(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<Person> findBySsn(String ssn) {
        return personRepository
                .findBySsn(ssn)
                .singleOptional()
                .flatMap(optional -> {
                    if (optional.isEmpty()) {
                        return Mono.error(NonExistentSsnException::new);
                    }
                    return Mono.just(optional.get());
                });
    }
}
