package com.api.v2.cards.services.interfaces;

import reactor.core.publisher.Mono;

public interface CardDeletionService {
    Mono<Void> delete(String id);
}
