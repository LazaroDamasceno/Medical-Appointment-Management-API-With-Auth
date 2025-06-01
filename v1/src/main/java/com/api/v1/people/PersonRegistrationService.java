package com.api.v1.people;

import com.api.v1.people.requests.PersonRegistrationDTO;

public interface PersonRegistrationService {
    Person register(PersonRegistrationDTO registrationDto);
}
