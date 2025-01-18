package com.api.v2.cards.services.impl;

import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.dtos.CardResponseDto;
import com.api.v2.cards.services.interfaces.CardRetrievalService;
import com.api.v2.cards.utils.CardFinderUtil;
import com.api.v2.cards.utils.CardResponseMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CardRetrievalServiceImpl implements CardRetrievalService {

    private final CardFinderUtil cardFinderUtil;
    private final CardRepository cardRepository;

    public CardRetrievalServiceImpl(
            CardFinderUtil cardFinderUtil,
            CardRepository cardRepository
    ) {
        this.cardFinderUtil = cardFinderUtil;
        this.cardRepository = cardRepository;
    }

    @Override
    public Flux<CardResponseDto> findAll() {
        return cardRepository
                .findAll()
                .flatMap(CardResponseMapper::mapToMono);
    }

    @Override
    public Mono<CardResponseDto> findById(String id) {
        return cardFinderUtil
                .find(id)
                .flatMap(CardResponseMapper::mapToMono);
    }
}
