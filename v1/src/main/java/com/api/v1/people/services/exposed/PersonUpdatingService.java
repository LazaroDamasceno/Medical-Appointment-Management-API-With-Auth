package com.api.v1.people.services.exposed;

import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonUpdatingDTO;

public interface PersonUpdatingService {
    Person update(Person person, PersonUpdatingDTO updatingDto);
}
