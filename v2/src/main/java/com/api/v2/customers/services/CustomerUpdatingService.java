package com.api.v2.customers.services;

import com.api.v2.people.requests.PersonUpdatingDto;
import reactor.core.publisher.Mono;

public interface CustomerUpdatingService {
    Mono<Void> update(String customerId, PersonUpdatingDto updatingDto);
}
