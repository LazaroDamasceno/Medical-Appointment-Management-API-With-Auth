package com.api.v1.people.services;

import com.api.v1.people.requests.PersonRegistrationDto;
import com.api.v1.people.models.Person;

public interface PersonRegistrationService {

    Person register(PersonRegistrationDto dto);
}
