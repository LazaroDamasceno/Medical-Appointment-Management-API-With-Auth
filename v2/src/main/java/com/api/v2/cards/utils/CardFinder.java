package com.api.v2.cards.utils;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.exceptions.NonExistentCardException;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CardFinder {

    private final CardRepository cardRepository;

    public CardFinder(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Mono<Card> find(String id) {
        return cardRepository
                .findById(id)
                .singleOptional()
                .flatMap(optional -> {
                   if (optional.isEmpty()) {
                       return Mono.error(new NonExistentCardException(id));
                   }
                   return Mono.just(optional.get());
                });
    }
}
