package com.api.v2.cards.services.interfaces;

import com.api.v2.cards.dtos.CardResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardRetrievalService {
    Flux<CardResponseDto> findAll();
    Mono<CardResponseDto> findById(String id);
}
