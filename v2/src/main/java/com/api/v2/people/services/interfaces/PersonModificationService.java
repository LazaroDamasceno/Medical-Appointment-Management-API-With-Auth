package com.api.v2.people.services.interfaces;

import com.api.v2.people.domain.Person;
import com.api.v2.people.dtos.PersonModificationDto;
import reactor.core.publisher.Mono;

public interface PersonModificationService {
    Mono<Person> modify(Person person, PersonModificationDto modificationDto);
}
