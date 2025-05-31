package com.api.v1.people.services.exposed;

import com.api.v1.people.Person;
import com.api.v1.people.requests.PersonUpdateDTO;

public interface PersonUpdateService {
    Person update(Person person, PersonUpdateDTO updatingDto);
}
