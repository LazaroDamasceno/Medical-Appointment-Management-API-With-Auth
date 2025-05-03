package com.api.v2.customers.services;

import com.api.v2.people.requests.PersonUpdatingDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface CustomerUpdatingService {
    Mono<ResponseEntity<Void>> update(String customerId, PersonUpdatingDto updatingDto);
}
