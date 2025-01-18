package com.api.v2.cards.services.impl;

import com.api.v2.cards.dtos.CardResponseDto;
import com.api.v2.cards.services.interfaces.CardFinderService;
import com.api.v2.cards.utils.CardFinderUtil;
import com.api.v2.cards.utils.CardResponseMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CardFinderServiceImpl implements CardFinderService {

    private final CardFinderUtil cardFinderUtil;

    public CardFinderServiceImpl(CardFinderUtil cardFinderUtil) {
        this.cardFinderUtil = cardFinderUtil;
    }

    @Override
    public Mono<CardResponseDto> findById(String id) {
        return cardFinderUtil
                .find(id)
                .flatMap(CardResponseMapper::mapToMono);
    }
}
