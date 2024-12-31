package com.api.v2.people.utils;

import com.api.v2.people.domain.Person;
import com.api.v2.people.domain.PersonRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PersonFinderUtil {

    private final PersonRepository repository;

    public PersonFinderUtil(PersonRepository repository) {
        this.repository = repository;
    }

    public Mono<Person> findBySsn(String ssn) {
        return repository
                .findAll()
                .filter(p -> p.getSsn().equals(ssn))
                .singleOrEmpty();
    }
}
