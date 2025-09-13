package com.api.v1;

import org.springframework.http.ResponseEntity;

public interface PersonRegistrationService {

    Person register(PersonRegistrationDto dto);
}
