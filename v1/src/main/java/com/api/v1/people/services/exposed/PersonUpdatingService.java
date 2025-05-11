package com.api.v1.people.services.exposed;

import com.api.v1.people.domain.Person;
import com.api.v1.people.requests.PersonUpdatingDto;

public interface PersonUpdatingService {
    Person update(Person person, PersonUpdatingDto updatingDto);
}
