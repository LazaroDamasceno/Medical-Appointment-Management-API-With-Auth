package com.api.v1.people;

import com.api.v1.people.requests.PersonUpdateDTO;

public interface PersonUpdateService {
    Person update(Person person, PersonUpdateDTO updatingDto);
}
