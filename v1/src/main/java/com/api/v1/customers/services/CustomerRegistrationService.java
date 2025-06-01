package com.api.v1.customers.services;

import com.api.v1.customers.responses.CustomerResponseDTO;
import com.api.v1.people.requests.PersonRegistrationDTO;
import org.springframework.http.ResponseEntity;

public interface CustomerRegistrationService {
    ResponseEntity<CustomerResponseDTO> register(PersonRegistrationDTO registrationDto);
}
