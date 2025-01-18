package com.api.v2.cards.utils;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.domain.CardRepository;
import com.api.v2.cards.exceptions.NonExistentCardException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CardFinderUtil {

    private final CardRepository cardRepository;

    public CardFinderUtil(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Mono<Card> find(String id) {
        return cardRepository
                .findById(new ObjectId(id))
                .singleOptional()
                .flatMap(optional -> {
                   if (optional.isEmpty()) {
                       return Mono.error(new NonExistentCardException(id));
                   }
                   return Mono.just(optional.get());
                });
    }
}
