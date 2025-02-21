package com.api.v2.cards.services.interfaces;

import com.api.v2.common.Id;

import reactor.core.publisher.Mono;

public interface CardDeletionService {
    Mono<Void> delete(@Id String id);
}
