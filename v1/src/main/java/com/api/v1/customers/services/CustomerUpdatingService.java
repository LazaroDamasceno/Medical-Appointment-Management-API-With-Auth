package com.api.v1.customers.services;

import com.api.v1.people.requests.PersonUpdatingDto;
import reactor.core.publisher.Mono;

public interface CustomerUpdatingService {
    Mono<Void> update(String customerId, PersonUpdatingDto updatingDto);
}
