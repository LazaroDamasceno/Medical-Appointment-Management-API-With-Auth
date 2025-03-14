package com.api.v2.cards.services.interfaces;

import com.api.v2.cards.dtos.CardResponseDto;

import de.kamillionlabs.hateoflux.model.hal.HalResourceWrapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CardRetrievalService {
    Flux<HalResourceWrapper<CardResponseDto, Void>> findAll();
    Mono<HalResourceWrapper<CardResponseDto, Void>> findById(String id);
}
