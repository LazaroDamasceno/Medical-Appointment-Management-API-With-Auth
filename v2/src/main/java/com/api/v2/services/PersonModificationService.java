package com.api.v2.services;

import com.api.v2.domain.Person;
import com.api.v2.dtos.PersonModificationDto;
import reactor.core.publisher.Mono;

public interface PersonModificationService {
    Mono<Person> modify(Person person, PersonModificationDto modificationDto);
}
