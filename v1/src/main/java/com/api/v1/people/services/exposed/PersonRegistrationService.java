package com.api.v1.people.services.exposed;

import com.api.v1.people.domain.exposed.Person;
import com.api.v1.people.requests.PersonRegistrationDTO;

public interface PersonRegistrationService {
    Person register(PersonRegistrationDTO registrationDto);
}
