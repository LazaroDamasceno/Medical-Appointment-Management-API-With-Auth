package com.api.v2.cards.services.interfaces;

import com.api.v2.cards.dtos.CardResponseDto;
import reactor.core.publisher.Mono;

public interface CardFinderService {
    Mono<CardResponseDto> findById(String id);
}
