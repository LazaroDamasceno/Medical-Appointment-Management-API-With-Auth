package com.api.v2.cards.utils;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.dtos.CardResponseDto;
import reactor.core.publisher.Mono;

public class CardResponseMapper {
    public static CardResponseDto mapToDto(Card card) {
        return new CardResponseDto(
                card.getId(),
                card.getType(),
                card.getBank(),
                card.getCvv_cvc(),
                card.getDueDate()
        );
    }

    public static Mono<CardResponseDto> mapToMono(Card card) {
        return Mono.just(mapToDto(card));
    }
}
