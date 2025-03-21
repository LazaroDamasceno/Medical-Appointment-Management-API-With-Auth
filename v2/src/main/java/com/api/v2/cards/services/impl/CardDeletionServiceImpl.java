package com.api.v2.cards.services.impl;

import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.services.interfaces.CardDeletionService;
import com.api.v2.cards.utils.CardFinder;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CardDeletionServiceImpl implements CardDeletionService {

    private final CardRepository cardRepository;
    private final CardFinder cardFinder;

    public CardDeletionServiceImpl(
            CardRepository cardRepository,
            CardFinder cardFinder
    ) {
        this.cardRepository = cardRepository;
        this.cardFinder = cardFinder;
    }

    @Override
    public Mono<Void> delete(String id) {
        return cardFinder
                .find(id)
                .flatMap(cardRepository::delete);
    }
}
