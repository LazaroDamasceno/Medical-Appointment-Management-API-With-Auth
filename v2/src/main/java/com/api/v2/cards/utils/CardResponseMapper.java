package com.api.v2.cards.utils;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.dtos.CardResponseDto;
import reactor.core.publisher.Mono;

public class CardResponseMapper {
    public static CardResponseDto mapToDto(Card card) {
        return new CardResponseDto(
                card.id().toString(),
                card.type(),
                card.bank(),
                card.cvv_cvc(),
                card.dueDate()
        );
    }

    public static Mono<CardResponseDto> mapToMono(Card card) {
        return Mono.just(mapToDto(card));
    }
}
