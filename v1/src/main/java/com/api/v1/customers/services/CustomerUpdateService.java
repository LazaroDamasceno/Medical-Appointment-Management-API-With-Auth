package com.api.v1.customers.services;

import com.api.v1.people.requests.PersonUpdateDTO;
import org.springframework.http.ResponseEntity;

public interface CustomerUpdateService {
    ResponseEntity<Void> update(String customerId, PersonUpdateDTO personUpdateDTO);
}
