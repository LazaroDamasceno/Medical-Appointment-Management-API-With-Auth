package com.api.v1.customers.services;

import com.api.v1.people.requests.PersonUpdatingDTO;
import org.springframework.http.ResponseEntity;

public interface CustomerUpdatingService {
    ResponseEntity<Void> update(String customerId, PersonUpdatingDTO personUpdatingDto);
}
