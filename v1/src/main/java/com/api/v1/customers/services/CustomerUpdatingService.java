package com.api.v1.customers.services;

import com.api.v1.people.requests.PersonUpdatingDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface CustomerUpdatingService {
    Mono<ResponseEntity<Void>> update(String customerId, PersonUpdatingDto updatingDto);
}
