package com.api.v2.cards.utils;

import com.api.v2.cards.domain.Card;
import com.api.v2.cards.dtos.CardResponseDto;

public class CardResponseMapper {
    public static CardResponseDto map(Card card) {
        return new CardResponseDto(
                card.id().toString(),
                card.type(),
                card.bank(),
                card.cvv_cvc(),
                card.dueDate()
        );
    }
}
